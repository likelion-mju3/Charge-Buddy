package chargebuddy.example.chargebuddy.Dto.map;

import chargebuddy.example.chargebuddy.Dto.review.ReviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "상권 마커 요약")
public class PlaceMarker {

    @Schema(description = "상권 ID(내부/외부 식별자)", example = "POI-12345")
    @NotBlank
    private String placeId;

    @Schema(description = "매장명", example = "스타카페 시청점")
    @NotBlank
    private String name;

    @Schema(description = "카테고리(내부 코드)", example = "CAFE")
    private String category;

    @Schema(description = "위도", example = "37.5664") @DecimalMin("-90.0") @DecimalMax("90.0")
    @NotNull
    private Double latitude;

    @Schema(description = "경도", example = "126.9778") @DecimalMin("-180.0") @DecimalMax("180.0")
    @NotNull
    private Double longitude;

    @Schema(description = "전화번호", example = "02-123-4567")
    private String phone;

    @Schema(description = "영업시간", example = "08:00 ~ 22:00")
    private String openHours;

//    @Schema(description = "리뷰 목록(응답 DTO)")
//    private List<ReviewResponse> reviews;
}