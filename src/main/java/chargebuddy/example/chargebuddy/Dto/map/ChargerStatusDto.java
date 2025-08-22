package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "개별 충전기 상태(공공API 원본 코드 매핑)")
public class ChargerStatusDto {

    @Schema(description = "충전기ID(chgerId)", example = "1")
    private String chgerId;

    @Schema(description = "충전기 타입(01~10)", example = "04")
    private String chgerType;

    @Schema(description = "충전 상태(0/1/2/3/4/5/9)", example = "2")
    private String stat;

    @Schema(description = "상태 갱신일시(yyyyMMddHHmmss)", example = "20250101123000")
    private LocalDateTime statUpdDt;

    @Schema(description = "마지막 충전 시작(yyyyMMddHHmmss)", example = "20241231120000")
    private LocalDateTime lastTsdt;

    @Schema(description = "마지막 충전 종료(yyyyMMddHHmmss)", example = "20241231131500")
    private LocalDateTime lastTedt;

    @Schema(description = "현재 충전 시작(yyyyMMddHHmmss)", example = "20250101113000")
    private LocalDateTime nowTsdt;

    @Schema(description = "출력(kW)", example = "100")
    private String output;

    @Schema(description = "충전방식(단독/동시 등)", example = "단독")
    private String method;

    @Schema(description = "파워 타입", example = "DC콤보")
    private String powerType;

    @Schema(description = "삭제 여부", example = "N")
    private String delYn;

    @Schema(description = "삭제 사유", example = "")
    private String delDetail;
}