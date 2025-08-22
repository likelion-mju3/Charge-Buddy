package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "충전소 상세/팝업 메타")
public class StationMarker {

    @Schema(description = "충전소 ID(statId)", example = "28260005")
    @NotBlank
    private String stationId;

    @Schema(description = "충전소 이름(statNm)", example = "기후대기관")
    @NotBlank
    private String stationName;

    @Schema(description = "주소(addr)", example = "인천광역시 서구 환경로 42")
    private String address;

    @Schema(description = "상세위치(location)", example = "입구 진입 후 2시 방향")
    private String location;

    @Schema(description = "위도(lat)", example = "37.569620")
    @DecimalMin("-90.0") @DecimalMax("90.0") @NotNull
    private Double latitude;

    @Schema(description = "경도(lng)", example = "126.641973")
    @DecimalMin("-180.0") @DecimalMax("180.0") @NotNull
    private Double longitude;

    // 운영기관
    @Schema(description = "운영기관 ID(busiId)", example = "ME")
    private String operatorBusId;

    @Schema(description = "운영기관명(bnm)", example = "환경부")
    private String operatorName;

    @Schema(description = "운영기관 연락처(busiCall)", example = "1661-9408")
    private String operatorCall;

    @Schema(description = "이용가능 시간(useTime)", example = "24시간 이용가능")
    private String useTime;

    // 집계
    @Schema(description = "사용 가능 충전기 수(stat=2)", example = "7") @Min(0)
    private Integer available;

    @Schema(description = "전체 충전기 수", example = "20") @Min(0)
    private Integer total;

    // 원본 코드 세트
    @Schema(description = "충전기 타입 코드 집합(01~10)", example = "[\"04\",\"06\"]")
    private Set<String> chgerTypes;

    @Schema(description = "충전기 상태 코드 집합(0/1/2/3/4/5/9)", example = "[\"2\",\"3\"]")
    private Set<String> statuses;

    // 기타 원본 필드
    @Schema(description = "주차료 무료 여부(parkingFree:Y/N)", example = "Y")
    private String parkingFree;

    @Schema(description = "이용제한 여부(limitYn:Y/N)", example = "N")
    private String limitYn;

    @Schema(description = "이용제한 사유(limitDetail)", example = "거주자외 출입제한")
    private String limitDetail;

    @Schema(description = "편의제공 여부(trafficYn:Y/N)", example = "N")
    private String trafficYn;

    @Schema(description = "충전소 구분 코드(kind)", example = "F0")
    private String kind;

    @Schema(description = "충전소 구분 상세 코드(kindDetail)", example = "F002")
    private String kindDetail;

    @Schema(description = "지역코드(zcode)", example = "28")
    private String zcode;

    @Schema(description = "지역상세코드(zscode)", example = "28260")
    private String zscode;

    @Schema(description = "추천 수(likes)", example = "12")
    private Integer likes;

    @Schema(description = "개별 충전기 상태 리스트")
    private List<ChargerStatusDto> chargers;
}