package chargebuddy.example.chargebuddy.Dto.map;

import chargebuddy.example.chargebuddy.Dto.review.ReviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "충전소 마커 요약")
public class StationMarker {

    @Schema(description = "충전소 ID(공공 statId 등)", example = "ST123456")
    @NotBlank
    private String stationId;

    @Schema(description = "충전소 이름", example = "서울시청 주차장")
    @NotBlank
    private String stationName;

    @Schema(description = "주소", example = "서울 중구 세종대로 110")
    @NotBlank
    private String address;

    @Schema(description = "사업자 코드(busid, 원본 코드)", example = "AC")
    private String operatorBusId;

    @Schema(description = "사업자 표시명(우리 매핑테이블로 resolve)", example = "환경부")
    private String operatorName;

    @Schema(description = "위도", example = "37.5665") @DecimalMin("-90.0") @DecimalMax("90.0")
    @NotNull
    private Double latitude;

    @Schema(description = "경도", example = "126.9780") @DecimalMin("-180.0") @DecimalMax("180.0")
    @NotNull
    private Double longitude;

    @Schema(description = "사용 가능 충전기 대수", example = "7") @Min(0)
    private Integer available;

    @Schema(description = "전체 충전기 대수", example = "20") @Min(0)
    private Integer total;

    @Schema(description = "충전기 타입(chgerType) 코드 배열", example = "[1,3,7]")
    private Set<Integer> chgerTypes;

    @Schema(description = "충전기 상태(stat) 코드 배열", example = "[2,3]")
    private Set<Integer> statuses; // 집계해서 넣는다면 stat 코드 그대로

    @Schema(description = "충전 요금(가공 텍스트)", example = "300원/kWh ~")
    private String feeText;

    @Schema(description = "운영시간(가공 텍스트)", example = "24시간")
    private String openHours;

    @Schema(description = "개별 충전기 상태 리스트")
    private List<ChargerStatusDto> chargers;

    @Schema(description = "리뷰 목록")
    private List<ReviewResponse> reviews;
}