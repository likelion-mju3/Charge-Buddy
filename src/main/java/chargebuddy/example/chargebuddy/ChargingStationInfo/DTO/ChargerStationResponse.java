package chargebuddy.example.chargebuddy.ChargingStationInfo.DTO;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Charger;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChargerStationResponse {
    private String statId;
    private String statNm;
    private String addr;
    private String useTime;
    private String bnm;
    private String parkingFree;
    private String limitYn;
    private String limitDetail;
    private double lat;
    private double lng;
    private String trafficYn;
    private String busiId;
    private String busiCall;
    private int likes;
    private List<ReviewResponse> reviews;
    private List<ChargerResponse> chargers;

    public ChargerStationResponse(ChargingStation chargingStation){
        this.statId = chargingStation.getStatId();
        this.statNm = chargingStation.getStatNm();
        this.addr = chargingStation.getAddr();
        this.useTime = chargingStation.getUseTime();
        this.bnm = chargingStation.getOperating().getBnm();
        this.parkingFree = chargingStation.getParkingFree();
        this.limitYn = chargingStation.getLimitYn();
        this.limitDetail = chargingStation.getLimitDetail();
        this.lat = chargingStation.getLat();
        this.lng = chargingStation.getLng();
        this.trafficYn = chargingStation.getTrafficYn();
        this.likes = chargingStation.getLikes();
        this.busiId = chargingStation.getOperating().getBusiId();
        this.busiCall = chargingStation.getBusiCall();
        List<ReviewResponse> result = new ArrayList<>();
        for(Review item : chargingStation.getReview()){
            ReviewResponse response = new ReviewResponse(item);
            result.add(response);
        }
        this.reviews = result;
        List<ChargerResponse> chargerResponseList = new ArrayList<>();
        for(Charger item : chargingStation.getCharger()){
            ChargerResponse chargerResponse = new ChargerResponse(item);
            chargerResponseList.add(chargerResponse);
        }
        this.chargers = chargerResponseList;
    }
}
