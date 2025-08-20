package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerApiForm;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Builder
public class ChargingStation {
    //충전소id
    @Id
    private String statId;

    //충전소명
    private String statNm;
    //주소
    private String addr;
    //상세위치
    private String location;
    //이용가능 시간
    private String useTime;
    //위도
    private double lat;
    //경도
    private double lng;
    //주차료 여부
    private String parkingFree;
    //충전소 설명
    private String note;
    //이용자 제한 여부
    private String limitYn;
    //이용자 제한 사유
    private String limitDetail;
    //편의제공 여부
    private String trafficYn;
    //충전소 구분 코드
    private String kind;
    //충전소 구분 상세 코드
    private String kindDetail;
    //운영 기관 연락처
    private String busiCall;

    //충전소 추천수
    @Setter
    private int likes = 0;

    //리뷰
    //@OneToMany(mappedBy = "chargingStation")
    //private List<ChargingStationReview> reviews = new ArrayList<>();

    //충전기
    @OneToMany(mappedBy = "chargingStation")
    private List<Charger> charger = new ArrayList<>();

    //지역코드(상세)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zscode")
    private RegionDetail regionDetail;

    //운영기관
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "busi_id")
    private Operating operating;

    public ChargingStation(ChargerApiForm item, Operating company, RegionDetail region){
        this.statNm = item.getStatNm();
        this.statId = item.getStatId();
        this.addr = item.getAddr();
        this.location = item.getLocation();
        this.busiCall = item.getBusiCall();
        this.useTime = item.getUseTime();
        this.lat = item.getLat();
        this.lng = item.getLng();
        this.parkingFree = item.getParkingFree();
        this.note = item.getNote();
        this.limitYn = item.getLimitYn();
        this.limitDetail = item.getLimitDetail();
        this.trafficYn = item.getTrafficYn();
        this.kind = item.getKind();
        this.kindDetail = item.getKindDetail();
        this.operating = company;
        this.regionDetail = region;
    }

    public void update(ChargerApiForm item, Operating company, RegionDetail region) {
        this.statNm = item.getStatNm();
        this.statId = item.getStatId();
        this.addr = item.getAddr();
        this.location = item.getLocation();
        this.busiCall = item.getBusiCall();
        this.useTime = item.getUseTime();
        this.lat = item.getLat();
        this.lng = item.getLng();
        this.parkingFree = item.getParkingFree();
        this.note = item.getNote();
        this.limitYn = item.getLimitYn();
        this.limitDetail = item.getLimitDetail();
        this.trafficYn = item.getTrafficYn();
        this.kind = item.getKind();
        this.kindDetail = item.getKindDetail();
        this.operating = company;
        this.regionDetail = region;
    }
}
