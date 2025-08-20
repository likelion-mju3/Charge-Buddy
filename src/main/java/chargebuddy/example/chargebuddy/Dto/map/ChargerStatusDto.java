package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Schema(description = "개별 충전기 상태")
public class ChargerStatusDto {

    @Schema(description = "충전기 식별자(내부 식별용)",
            example = "CH-001")
    private String chargerId;

    @Schema(description = "충전기 타입 코드(chgerType)",
            example = "3")
    private Integer chgerType;

    @Schema(description = "충전기 상태(stat) 코드 (1:통신이상, 2:충전대기, 3:충전중, 4:운영중지, 5:점검중, 9:상태미확인)",
            example = "2")
    private Integer stat;

    @Schema(description = "상태갱신일시(statUpdDt, yyyymmddHHMMSS)", example = "20240820121020")
    private String statUpdDt;

    @Schema(description = "마지막 충전 시작일시(lastTsdt, yyyymmddHHMMSS)", example = "20240801121020")
    private String lastTsdt;

    @Schema(description = "마지막 충전 종료일시(lastTedt, yyyymmddHHMMSS)", example = "20240801123020")
    private String lastTedt;

    @Schema(description = "충전중 시작일시(nowTsdt, yyyymmddHHMMSS)", example = "20240802131020")
    private String nowTsdt;

    @Schema(description = "충전용량(output, kW: 3, 7, 50, 100, 200…)", example = "50")
    private Integer output;

    @Schema(description = "충전방식(method: 단독/동시)", example = "단독")
    private String method;
}