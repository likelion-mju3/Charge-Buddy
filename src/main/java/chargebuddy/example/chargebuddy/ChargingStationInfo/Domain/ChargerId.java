package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

public class ChargerId implements Serializable {
    private static final long serialVersionUID = 5735022999922182249L;

    @EqualsAndHashCode.Include
    public String statId;

    @EqualsAndHashCode.Include
    public String ChgerId;
}
