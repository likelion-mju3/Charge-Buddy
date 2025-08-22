package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.DTO.ReviewResponse;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Review;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.StationReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StationReviewService {
    private final StationReviewRepository stationReviewRepository;
    private final ChargingStationService chargingStationService;

    public ReviewResponse create(String statId, String userName, String password, String reviewText, LocalDateTime reviewTime) {
        Review review = Review.builder()
                .chargingStation(chargingStationService.findById(statId))
                .userName(userName)
                .password(password)
                .reviewText(reviewText)
                .reviewTime(reviewTime != null ? reviewTime : LocalDateTime.now())
                .build();

        stationReviewRepository.saveAndFlush(review);
        return new ReviewResponse(review);
    }

    @Transactional
    public List<ReviewResponse> getByStatId(String statId){
        List<ReviewResponse> result = new ArrayList<>();
        for(Review item : stationReviewRepository.findByChargingStationStatId(statId)){
            ReviewResponse response = new ReviewResponse(item);
            result.add(response);
        }
        return result;
    }

    public ReviewResponse update(Long id, String password, String reviewText) {
        Review r = stationReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을수 없습니다."));

        if (r.getPassword() == null || !r.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if(reviewText != null) r.setReviewText(reviewText);

        ReviewResponse response = new ReviewResponse(stationReviewRepository.saveAndFlush(r));

        return response;
    }

    public void delete(Long id, String password) {
        Review r = stationReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을수 없습니다."));
        if (r.getPassword() == null || !r.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        stationReviewRepository.delete(r);
    }

    @Transactional
    public ReviewResponse addLike(Long id){
        Review r = stationReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        r.setLikes(r.getLikes()+1);
        return new ReviewResponse(stationReviewRepository.saveAndFlush(r));
    }
}
