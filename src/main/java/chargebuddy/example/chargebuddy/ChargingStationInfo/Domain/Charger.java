package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerApiForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@IdClass(ChargerId.class)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
public class Charger {


    //충전기랑 조인
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_Id")
    private ChargingStation chargingStation;

    //충전기 id
    @Id
    private String chgerId;
    //충전기 타입
    private String chgerType;
    //충전기 파워 타입
    private String powerType;
    //충전기 상태
    private String stat;
    //충전기 상태 갱신 일시
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime statUpdDt;
    //마지막 충전 시작일
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime lastTsdt;
    //마지막 충전종료일시
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime lastTedt;
    //충전중 시작일시
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime nowTsdt;
    //충전 용량(kw)
    private String output;
    //충전 방식(단독/동시)
    private String method;
    //삭제 여부
    private String delYn;
    //삭제 사유
    private String delDetail;

    public Charger(ChargerApiForm item, ChargingStation station) {
        this.chargingStation = station;
        this.chgerId = item.getChgerId();
        this.chgerType = item.getChgerType();
        this.powerType = item.getPowerType();
        this.stat = item.getStat();
        this.statUpdDt = item.getStatUpdDt();
        this.lastTsdt = item.getLastTsdt();
        this.lastTedt = item.getLastTedt();
        this.nowTsdt = item.getNowTsdt();
        this.method = item.getMethod();
        this.output = item.getOutput();
        this.delYn = item.getDelYn();
        this.delDetail = item.getDelDetail();
    }


    public void update(ChargerApiForm item, ChargingStation station) {
        this.chargingStation = station;
        this.chgerId = item.getChgerId();
        this.chgerType = item.getChgerType();
        this.powerType = item.getPowerType();
        this.stat = item.getStat();
        this.statUpdDt = item.getStatUpdDt();
        this.lastTsdt = item.getLastTsdt();
        this.lastTedt = item.getLastTedt();
        this.nowTsdt = item.getNowTsdt();
        this.method = item.getMethod();
        this.output = item.getOutput();
        this.delYn = item.getDelYn();
        this.delDetail = item.getDelDetail();
    }
}
