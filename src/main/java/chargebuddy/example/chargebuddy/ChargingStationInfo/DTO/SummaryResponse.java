package chargebuddy.example.chargebuddy.ChargingStationInfo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SummaryResponse {
    private String statId;
    private String summary;

    public SummaryResponse(String statId, String summary){
        this.statId = statId;
        this.summary = summary;
    }
}
