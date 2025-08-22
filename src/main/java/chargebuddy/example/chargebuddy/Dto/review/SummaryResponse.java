package chargebuddy.example.chargebuddy.Dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Schema(description="리뷰 요약 응답")
public class SummaryResponse {
    @Schema(description="placeId", example="ChIJxxxxxxxx")
    private String placeId;

    @Schema(description="요약 텍스트(bullet 한국어)")
    private String summary;
}