package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "지도 검색 요청(사각형 뷰포트 + 필터)")
public class MapSearchRequest {

    // 사각형 뷰포트 (남서-북동)
    @Schema(description = "남서 위도", example = "37.5600", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin("-90.0") @DecimalMax("90.0") @NotNull
    private Double swLat;

    @Schema(description = "북동 위도", example = "37.5900", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin("-90.0") @DecimalMax("90.0") @NotNull
    private Double neLat;

    @Schema(description = "남서 경도", example = "126.9700", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin("-180.0") @DecimalMax("180.0") @NotNull
    private Double swLng;

    @Schema(description = "북동 경도", example = "126.9900", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin("-180.0") @DecimalMax("180.0") @NotNull
    private Double neLng;

    // 공공 API 원본 코드값 pass-through
    @Schema(description = "충전기 타입 코드(01~10)", example = "[\"04\",\"06\",\"10\"]")
    private List<@Pattern(regexp = "^[0-9]{2}$") String> chgerTypes;

    @Schema(description = "이용자 제한 여부(Y/N)", example = "N")
    @Pattern(regexp = "^[YN]$")
    private String limitYn;

    @Schema(description = "충전기 상태(0/1/2/3/4/5/9)", example = "2")
    @Pattern(regexp = "^[0|1|2|3|4|5|9]$")
    private String stat;
}