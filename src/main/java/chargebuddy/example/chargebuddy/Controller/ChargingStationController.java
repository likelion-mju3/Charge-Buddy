package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargerType;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargerService;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChargingStationController {
    private final ChargingStationService chargingStationService;

    //기본 장소 명지대학교 인문캠퍼스
    private static final String DEFAULT_LAT = "37.5803";
    private static final String DEFAULT_LNG = "126.9225";

    //지도 내 충전소 검색
    @GetMapping("/map")
    public List<ChargingStation> searchStation(@RequestParam double swLat, @RequestParam double neLat, @RequestParam double swLng, @RequestParam double neLng){
        return chargingStationService.findByLatBetweenAndLngBetween(swLat, neLat, swLng, neLng);
    }

    //검색 조건
    @GetMapping("/map")
    public List<ChargingStation> searchBaseStation(
            //충전 타입
            @RequestParam(value = "chgerType", defaultValue = "") List<String> chgerTypes,
            //이용제한 여유
            @RequestParam(value = "limitYn", defaultValue = "") String limitYn,
            //충전 가능 여부
            @RequestParam(value = "stat", defaultValue = "") String stat,
            //지도 내 영역
            @RequestParam double swLat, @RequestParam double neLat,
            @RequestParam double swLng, @RequestParam double neLng
    ) {
        return chargingStationService.searchBaseStation(chgerTypes, limitYn, stat, swLat, neLat, swLng, neLng);
    }

    //추천 수
    @PostMapping("/map/{statId}/like")
    public Optional<ChargingStation> likeStation(@RequestParam String statId){
        ChargingStation updated = chargingStationService.addLike(statId);
        return Optional.ofNullable(updated);
    }


}
