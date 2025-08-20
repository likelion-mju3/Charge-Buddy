package chargebuddy.example.chargebuddy.ChargingStationInfo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ChargerStateUpdateForm {
    private String statId;
    private String chgerId;
    private String stat;
    private LocalDateTime statUpdDt;
    private LocalDateTime lastTsdt;
    private LocalDateTime lastTedt;
    private LocalDateTime nowTsdt;
}
