package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.Service.ReviewSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
@Tag(name = "리뷰 요약", description = "충전소 리뷰 요약 API")
public class SummaryController {
    private final ReviewSummaryService summaryService;



    @Operation(
            summary = "충전소 리뷰 요약 조회",
            description = "특정 충전소(placeId)의 최근 리뷰를 요약해 반환합니다. "
                    + "리뷰가 없거나 요약이 불가한 경우 204(No Content)를 반환합니다."
    )
    @GetMapping("/{placeId}")
    public ResponseEntity<String> summarize(@PathVariable String placeId) {
        String result = summaryService.summarizeNow(placeId);
        return (result == null || result.isBlank())
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(result);
    }
}