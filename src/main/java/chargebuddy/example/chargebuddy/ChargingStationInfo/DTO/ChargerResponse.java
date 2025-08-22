package chargebuddy.example.chargebuddy.ChargingStationInfo.DTO;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Charger;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChargerResponse {
    private Long id;
    private String chgerId;
    private String chgerType;
    private String powerType;
    private String stat;
    private LocalDateTime statUpdDt;
    private LocalDateTime lastTsdt;
    private LocalDateTime lastTedt;
    private LocalDateTime nowTsdt;
    private String output;
    private String method;

    public ChargerResponse(Charger charger){
        this.id = charger.getId();
        this.chgerId = charger.getChgerId();
        this.chgerType = charger.getChgerType();
        this.powerType = charger.getPowerType();
        this.stat = charger.getStat();
        this.statUpdDt = charger.getStatUpdDt();
        this.lastTsdt = charger.getLastTsdt();
        this.lastTedt = charger.getLastTedt();
        this.nowTsdt = charger.getNowTsdt();
        this.output = charger.getOutput();
        this.method = charger.getMethod();
    }
}
