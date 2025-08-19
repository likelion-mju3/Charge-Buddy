package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.Domain.StationReview;
import chargebuddy.example.chargebuddy.Service.StationReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class StationReviewController {

    private final StationReviewService stationreviewService;


    // CREATE
    @PostMapping("/{placeId}")
    public String createReview(@PathVariable String placeId,
                               @RequestBody Map<String, Object> request) {
        String userName = request.get("사용자명").toString();
        String password = request.get("비밀번호").toString();
        String reviewText = request.get("리뷰").toString();
        stationreviewService.create(placeId, userName, password, reviewText, null);
        return "리뷰 작성이 완료되었습니다";}



    // READ
    @GetMapping("/{placeId}")
    public List<StationReview> getReviewsByStation(@PathVariable String placeId) {
        return stationreviewService.getByPlaceId(placeId);
    }

    // UPDATE
    @PatchMapping("/{placeId}/{reviewId}")
    public String updateReview(@PathVariable String placeId,
                               @PathVariable Long reviewId,
                               @RequestBody Map<String, Object> request) {
        String password = request.get("비밀번호").toString();
        String reviewText = request.get("리뷰").toString();

        stationreviewService.update(reviewId, password, null, reviewText);
        return "리뷰 수정이 완료되었습니다";
    }


    // DELETE
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id,
                               @RequestParam String password) {
        stationreviewService.delete(id, password);
        return "리뷰가 삭제되었습니다.";
    }


    // 추천 수 반영
    @PostMapping("/{reviewId}/like")
    public String likeReview(@PathVariable Long reviewId) {
        StationReview updated = stationreviewService.addLike(reviewId);
        return "추천 완료! 현재 추천 수: " + updated.getLikes();
    }
}