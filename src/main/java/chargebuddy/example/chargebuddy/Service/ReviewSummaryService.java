package chargebuddy.example.chargebuddy.Service;
import java.util.Optional;
import chargebuddy.example.chargebuddy.Domain.ReviewSummary;
import chargebuddy.example.chargebuddy.Repository.ReviewSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewSummaryService {
    private final ReviewSummaryRepository reviewSummaryRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private final String API_KEY = "nv-473b902fde334f1da904200949e66501lg8z";
    private final String URL = "https://clovastudio.stream.ntruss.com/v3/chat-completions/HCX-005";

    /**
     * 특정 장소의 리뷰 요약 생성 & 저장 - naver 클로바 노트 사용
     */
    public ReviewSummary summarizeAndSave(String placeId,  List<String> reviews) {
        String reviewText = String.join("\n", reviews);

        // 요청 Body
        Map<String, Object> body = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();

        messages.add(Map.of(
                "role", "system",
                "content", List.of(Map.of("type", "text",
                        "text", "- 당신은 리뷰 요약 어시스턴트입니다.\n- 주어진 리뷰들을 분석해 장점, 단점의 핵심만 요약하세요."))
        ));

        messages.add(Map.of(
                "role", "user",
                "content", List.of(Map.of("type", "text", "text", reviewText))
        ));

        body.put("messages", messages);
        body.put("topP", 0.6);
        body.put("topK", 0);
        body.put("maxTokens", 512);
        body.put("temperature", 0.1);
        body.put("repetitionPenalty", 1.1);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("API KEY 들어가야함", UUID.randomUUID().toString());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        String response = restTemplate.postForObject(URL, entity, String.class);

        // DB 저장 1번 저장하면 끝 (실시간 변화 X)
        ReviewSummary summary = ReviewSummary.builder()
                .placeId(placeId)
                .summaryText(response)  // 요약 추출
                .createdAt(LocalDateTime.now())
                .build();

        return reviewSummaryRepository.save(summary);
    }



    public Optional<ReviewSummary> findByPlaceId(String placeId) {
        return reviewSummaryRepository.findByPlaceId(placeId);
    }
}
