package chargebuddy.example.chargebuddy.Dto.map;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "개별 충전기 상태")
public class ChargerStatusDto {
    @Schema(description = "충전기 식별자", example = "CH-001")
    private String chargerId;

    @Schema(description = "타입(chgerType) 코드", example = "3")
    private Integer chgerType;

    @Schema(description = "상태(stat) 코드", example = "2")
    private Integer status;

    @Schema(description = "출력(kW) 등 부가", example = "100")
    private Integer outputKw;

    @Schema(description = "고장 여부", example = "false")
    private Boolean broken;

    @Schema(description = "사용 중 여부", example = "true")
    private Boolean inUse;
}