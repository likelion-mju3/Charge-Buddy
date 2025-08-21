package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChargerId implements Serializable {

    private String chgerId;

    private ChargingStation chargingStation;
}
