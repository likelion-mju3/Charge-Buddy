package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "충전소 마커 요약(목록/지도용)")
public class StationMarkerSummary {

    @Schema(description = "충전소 ID(statId)", example = "28260005")
    @NotBlank
    private String stationId;

    @Schema(description = "충전소 이름(statNm)", example = "기후대기관")
    @NotBlank
    private String stationName;

    @Schema(description = "위도(lat)", example = "37.569620")
    @DecimalMin("-90.0") @DecimalMax("90.0") @NotNull
    private Double latitude;

    @Schema(description = "경도(lng)", example = "126.641973")
    @DecimalMin("-180.0") @DecimalMax("180.0") @NotNull
    private Double longitude;

    @Schema(description = "사용 가능 충전기 수(stat=2)", example = "7")
    @Min(0)
    private Integer available;

    @Schema(description = "전체 충전기 수", example = "20")
    @Min(0)
    private Integer total;

    @Schema(description = "충전기 타입 코드 집합(01~10)", example = "[\"04\",\"06\"]")
    private Set<String> chgerTypes;

    @Schema(description = "충전기 상태 코드 집합(0/1/2/3/4/5/9)", example = "[\"2\",\"3\"]")
    private Set<String> statuses;

    @Schema(description = "추천 수", example = "12")
    private Integer likes;
}