package chargebuddy.example.chargebuddy.Service;


import chargebuddy.example.chargebuddy.Domain.StationReview;
import chargebuddy.example.chargebuddy.Repository.StationReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class GoogleReviewService {

    private final StationReviewRepository StationreviewRepository;
    private final String API_KEY = "구글 API KEY"; // 구글 리뷰 가져오기위한 KEY



    public void fetchAndSaveReviews(String placeId, String stationName) {
        String url = "https://places.googleapis.com/v1/places/" + placeId
                + "?fields=reviews&key=" + API_KEY;


        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);

        JSONObject root = new JSONObject(jsonResponse);
        if (!root.has("reviews")) return;

        JSONArray reviews = root.getJSONArray("reviews");

        for (int i = 0; i < reviews.length(); i++) {
            JSONObject r = reviews.getJSONObject(i);

            String authorName = r.optString("authorName");
            int likes = r.optInt("rating");
            String text = r.optJSONObject("text").optString("text");
            String publishTime = r.optString("publishTime");

            // ISO-8601 → LocalDateTime 변환
            LocalDateTime reviewTime = LocalDateTime.parse(
                    publishTime,
                    DateTimeFormatter.ISO_DATE_TIME
            ).atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();


            // DB에 동일한 리뷰가 없을 때만 저장
            boolean exists = StationreviewRepository
                    .findByPlaceIdAndUserNameAndReviewText(placeId, authorName, text)
                    .isPresent();



            if (!exists) {
                StationReview review = StationReview.builder()
                        .placeId(placeId)
                        .stationName(stationName)
                        .userName(authorName) // API에서 받은 작성자명
                        .password(null)       // 외부 API 데이터라 null 처리
                        .likes(likes)
                        .reviewText(text)
                        .reviewTime(reviewTime)
                        .build();

                StationreviewRepository.save(review);

            }
        }
    }

    public List<StationReview> getAllReviews() {
        return StationreviewRepository.findAll();
    }

}
