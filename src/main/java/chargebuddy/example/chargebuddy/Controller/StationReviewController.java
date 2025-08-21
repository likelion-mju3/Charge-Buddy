package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.Domain.StationReview;
import chargebuddy.example.chargebuddy.Service.StationReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "리뷰 CRUD", description = "충전소 리뷰 등록/조회/수정/삭제 및 추천")
public class StationReviewController {

    private final StationReviewService stationreviewService;


    // CREATE
    @Operation(
            summary = "리뷰 등록",
            description = "특정 충전소(placeId)에 대해 사용자 리뷰를 등록합니다.")
    @PostMapping("/{placeId}")
    public String createReview(@PathVariable String placeId,
                               @RequestBody Map<String, Object> request) {
        String userName = request.get("사용자명").toString();
        String password = request.get("비밀번호").toString();
        String reviewText = request.get("리뷰").toString();
        stationreviewService.create(placeId, userName, password, reviewText, null);
        return "리뷰 작성이 완료되었습니다";}


    @Operation(
            summary = "충전소 리뷰 목록 조회",
            description = "placeId 기준으로 등록된 리뷰 목록을 반환합니다. 최신순 등 정렬은 서비스/레포지토리 구현에 따릅니다."
    )
    // READ
    @GetMapping("/{placeId}")
    public List<StationReview> getReviewsByStation(@PathVariable String placeId) {
        return stationreviewService.getByPlaceId(placeId);
    }

    // UPDATE
    @Operation(
            summary = "리뷰 수정",
            description = "리뷰 본문을 수정합니다. 비밀번호가 일치해야 수정됩니다."
    )
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
    @Operation(
            summary = "리뷰 삭제",
            description = "리뷰를 삭제합니다. 비밀번호가 일치해야 삭제됩니다."
    )
    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id,
                               @RequestParam String password) {
        stationreviewService.delete(id, password);
        return "리뷰가 삭제되었습니다.";
    }


    // 추천 수 반영
    @Operation(
            summary = "리뷰 추천(좋아요) 증가",
            description = "리뷰의 추천 수를 1 증가시킵니다."
    )
    @PostMapping("/{reviewId}/like")
    public String likeReview(@PathVariable Long reviewId) {
        StationReview updated = stationreviewService.addLike(reviewId);
        return "추천 완료! 현재 추천 수: " + updated.getLikes();
    }
}