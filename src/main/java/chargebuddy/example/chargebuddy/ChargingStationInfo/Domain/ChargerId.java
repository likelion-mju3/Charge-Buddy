package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ChargerId implements Serializable {

    public String statId;

    public String ChgerId;
}
