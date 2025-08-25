package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.ChargingStationInfo.DTO.ReviewResponse;
import chargebuddy.example.chargebuddy.ChargingStationInfo.DTO.SummaryResponse;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Review;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ReviewSummaryService;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.StationReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://www.chargebuddy.digital", "https://chargebuddy.digital", "http://localhost:8080", "http://localhost:5173"})
@Tag(name = "리뷰 CRUD API", description = "충전소 리뷰/등록/조회/삭제 및 추천")
public class StationReviewController {
    private final StationReviewService stationReviewService;
    private final ReviewSummaryService reviewSummaryService;

    //CREATE
    @Operation(summary = "리뷰등록", description = "특정 충전소(statId)에 대해 사용자 리뷰를 등록합니다. 닉네임, 비밀번호, 리뷰가 필요합니다.")
    @PostMapping("/{statId}")
    public ReviewResponse createReview(@PathVariable String statId, @RequestBody Map<String, Object> request){
        String userName = request.get("사용자명").toString();
        String password = request.get("비밀번호").toString();
        String reviewText = request.get("리뷰").toString();
        return stationReviewService.create(statId, userName, password, reviewText, null);
    }


    @Operation(
            summary = "충전소 리뷰 목록 조회",
            description = "statId 기준으로 등록된 리뷰 목록을 반환합니다. 최신순 등 정렬은 서비스/레포지토리 구현에 따릅니다."
    )
    // READ
    @GetMapping("/{statId}")
    public List<ReviewResponse> getReviewsByStation(@PathVariable String statId) {
        return stationReviewService.getByStatId(statId);
    }

    @Operation(summary = "충전소 리뷰 요약", description = "충전소(statId)별로 등록된 최신 리뷰 5개를 요약합니다.")
    @GetMapping("/{statId}/summary")
    public SummaryResponse summarize(@PathVariable String statId){
        return reviewSummaryService.summarizeNow(statId);
    }

    // UPDATE
    @Operation(
            summary = "리뷰 수정",
            description = "리뷰 본문을 수정합니다. 비밀번호가 일치해야 수정됩니다."
    )
    @PatchMapping("/{statId}/{reviewId}")
    public ReviewResponse updateReview(@PathVariable String statId,
                                       @PathVariable Long reviewId,
                               @RequestBody Map<String, Object> request) {
        String password = request.get("비밀번호").toString();
        String reviewText = request.get("리뷰").toString();

        return stationReviewService.update(reviewId, password, reviewText);
    }


    // DELETE
    @Operation(
            summary = "리뷰 삭제",
            description = "리뷰를 삭제합니다. 비밀번호가 일치해야 삭제됩니다."
    )
    @DeleteMapping("/{statId}/{reviewId}")
    public String deleteReview(@PathVariable String statId,
                               @PathVariable Long reviewId,
                               @RequestParam String password) {
        stationReviewService.delete(reviewId, password);
        return "리뷰가 삭제되었습니다.";
    }

//    @Operation(
//            summary = "리뷰 삭제(관리자용)",
//            description = "리뷰를 삭제합니다. 관리자용으로 임의삭제시 사용"
//    )
//    @DeleteMapping("/{statId}/{reviewId}")
//    public String delete(@PathVariable String statId,
//                         @PathVariable Long reviewId){
//        stationReviewService.delete(reviewId);
//        return "리뷰가 삭제되었습니다.";
//    }


    // 추천 수 반영
    @Operation(
            summary = "리뷰 추천(좋아요) 증가",
            description = "리뷰의 추천 수를 1 증가시킵니다."
    )
    @PostMapping("/{statId}/{reviewId}/like")
    public ReviewResponse likeReview(@PathVariable String statId,
                                     @PathVariable Long reviewId) {
        return stationReviewService.addLike(reviewId);
    }

}
