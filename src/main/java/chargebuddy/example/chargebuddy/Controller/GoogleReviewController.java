package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.Domain.StationReview;
import chargebuddy.example.chargebuddy.Service.GoogleReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/google/reviews")
@RequiredArgsConstructor
public class GoogleReviewController {

    private final GoogleReviewService googleReviewService;


    // 👉 데모 실행 메서드
    @PostMapping("/demo1")
    public String fetchDemoReviews1() {
        googleReviewService.fetchAndSaveReviews("장소 API 들어가야 함", "교보문고 광화문점");
        return "Demo reviews fetched and saved!";
    }


    @PostMapping("/demo2")
    public String fetchDemoReviews2() {
        googleReviewService.fetchAndSaveReviews("장소 API 들어가야 함", "테슬라 수퍼차저");
        return "Demo reviews fetched and saved!";
    }

    @PostMapping("/demo3")
    public String fetchDemoReviews3() {
        googleReviewService.fetchAndSaveReviews("장소 API 들어가야 함", "서울숲");
        return "Demo reviews fetched and saved!";
    }



    @GetMapping
    public List<StationReview> getAllReviews() {
        return googleReviewService.getAllReviews();
    }
}
