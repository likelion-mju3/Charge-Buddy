package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지도 내 충전소 조회 요청")
public class MapPlaceRequest {

    // ERD: charging_station.longitude
    @Schema(description = "경도(lng)",
            example = "126.9780",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin("-180.0") @DecimalMax("180.0") @NotNull
    private Double longitude;

    // ERD: charging_station.latitude
    @Schema(description = "위도(lat)",
            example = "37.5665",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin("-90.0") @DecimalMax("90.0") @NotNull
    private Double latitude;

    @Schema(description = "지도 줌 레벨(기본 12)",
            example = "12",
            defaultValue = "12")
    @Min(1) @Max(20)
    private Integer zoomLevel = 12;

    // 검색 반경
    @Schema(description = "검색 반경(km, 기본 5)",
            example = "5",
            defaultValue = "5")
    @Min(1) @Max(50)
    private Integer radiusKm = 5;

    @Schema(description = "충전기 타입(chgerType) 코드 배열",
            example = "[1,3,7]")
    private Set<Integer> chgerTypes;

    @Schema(description = "충전기 상태(stat) 코드 배열(예: 사용가능 등)",
            example = "[2]")
    private Set<Integer> statuses;

    @Schema(description = "사업자(busid) 코드 배열",
            example = "[\"AC\",\"AL\",\"BP\",\"CS\"]")
    private Set<String> busIds;

    // 선호상권
    // (선택) 우리 서비스 상권 카테고리 필터
    @Schema(description = "상권 카테고리(내부 코드)",
            example = "CAFE")
    private String poiCategory;
}