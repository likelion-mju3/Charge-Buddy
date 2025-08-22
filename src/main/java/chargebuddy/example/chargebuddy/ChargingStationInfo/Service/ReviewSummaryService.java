package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Review;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.ChargingStationRepository;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.StationReviewRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.cache.spi.entry.CacheEntry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static chargebuddy.example.chargebuddy.AppConfig.summeryKey;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewSummaryService {
    private final StationReviewRepository stationReviewRepository;
    private final RestTemplate restTemplate;
    private final ChargingStationRepository chargingStationRepository;

    private String URL = "https://clovastudio.stream.ntruss.com/v3/chat-completions/HCX-005";

    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

    @Getter
    @AllArgsConstructor
    private static class CacheEntry {
        private final LocalDateTime lastMaxTime;
        private final int lastCount;
        private final String summary;
    }

    /**
     * 매 요청마다:
     * 1) 리뷰 없으면 요약 안 함(빈 문자열 반환 → 컨트롤러에서 204 처리 권장)
     * 2) 새 리뷰 생겼을 때만 클로바 재호출 (count/max(reviewTime) 기준)
     * 3) DB 저장 없음. 문자열 요약만 즉시 반환.
     */
    public String summarizeNow(String statId) {
        Optional<ChargingStation> chargingStation = chargingStationRepository.findById(statId);
        ChargingStation chargingStation1 = chargingStation.get();
        int count = chargingStation1.getReview().size();
        if (count == 0) return ""; // 리뷰 없음

        //LocalDateTime lastTime = reviewRepository.findLastReviewTime(statId);
        //CacheEntry cached = cache.get(statId);
//        if (cached != null
//                && cached.getLastCount() == count
//                && Objects.equals(cached.getLastMaxTime(), lastTime)) {
//            return cached.getSummary(); // 새 리뷰 없음 → 캐시 사용
//        }
        List<Review> recent = stationReviewRepository.findTop5ByStatIdOrderByReviewTimeDesc(statId);
        String prompt = buildPrompt(recent);
        String summary = callClovaAndExtract(prompt);

        //cache.put(statId, new CacheEntry(lastTime, count, summary));
        return summary;
    }

    // ----- 프롬프트 구성 -----
    private String buildPrompt(List<Review> reviews) {
        String bullets = reviews.stream()
                .map(r -> String.format("- [%d추천] %s", r.getLikes(), safe(r.getReviewText())))
                .collect(Collectors.joining("\n"));

        return """
                아래는 특정 전기차 충전소에 대한 최근 사용자 리뷰 목록입니다.
                중복 표현은 합치고, 핵심만 간결히 bullet 형식의 한국어로 요약하세요.
                '충전 속도/고장/대기/주차/요금/화장실/편의시설' 항목을 우선 정리해주세요.
                장점과 단점이 드러나게 정리해주세요.
                
                리뷰:
                %s
                """.formatted(bullets);
    }

    private static String safe(String s) { return (s == null) ? "" : s; }


    private String callClovaAndExtract(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + summeryKey);
        headers.set(summeryKey, UUID.randomUUID().toString());

        // 요청 Body
        Map<String, Object> body = new HashMap<>();
        List<Map<String, Object>> messages = new ArrayList<>();

        messages.add(Map.of(
                "role", "system",
                "content", List.of(Map.of("type", "text",
                        "text",  """
                               - 당신은 전기차 충전소 리뷰 요약 어시스턴트입니다.
                               - 중복/군더더기는 합치고, 핵심만 간결하게 요약합니다.
                               - '충전 속도/고장/대기/주차/요금/화장실/편의시설'을 우선 정리합니다.
                               - Bullet 형식의 한국어 요약으로 출력합니다.
                               """))
        ));

        messages.add(Map.of(
                "role", "user",
                "content", List.of(Map.of("type", "text", "text", prompt))
        ));

        body.put("messages", messages);
        body.put("topP", 0.6);
        body.put("topK", 0);
        body.put("maxTokens", 512);
        body.put("temperature", 0.1);
        body.put("repetitionPenalty", 1.1);


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp;
        try {
            resp = restTemplate.postForEntity(URL, entity, Map.class);
        } catch (Exception e) {
            // 실패 시 빈 문자열 반환(프론트에서 “요약 불가” 처리)
            return "";
        }

        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
            return "";
        }

        return extractSummaryFromBody(resp.getBody());
    }

    @SuppressWarnings("unchecked")
    private String extractSummaryFromBody(Map<String, Object> body) {
        // 1) result.output
        Object result = body.get("result");
        if (result instanceof Map<?, ?> r) {
            Object output = r.get("output");
            if (output != null) return output.toString();

            // 2) result.message.content[0].text
            Object message = r.get("message");
            if (message instanceof Map<?, ?> m) {
                Object content = m.get("content");
                if (content instanceof List<?> lst && !lst.isEmpty()) {
                    Object first = lst.get(0);
                    if (first instanceof Map<?, ?> f) {
                        Object text = f.get("text");
                        if (text != null) return text.toString();
                    }
                }
            }
        }

        Object choices = body.get("choices");
        if (choices instanceof List<?> lst && !lst.isEmpty()) {
            Object first = lst.get(0);
            if (first instanceof Map<?, ?> f) {
                Object msg = f.get("message");
                if (msg instanceof Map<?, ?> m) {
                    Object content = m.get("content");
                    if (content != null) return content.toString();
                }
            }
        }

        // 4) 마지막 fallback: 문자열화
        return body.toString();
    }

}
