package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.ChargingStationInfo.DTO.ChargerStationResponse;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargingStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "ChargeStationInfoAPI", description = "충전소 정보 API")
public class ChargingStationController {
    private final ChargingStationService chargingStationService;

    //기본 장소 명지대학교 인문캠퍼스
    private static final String DEFAULT_LAT = "37.5803";
    private static final String DEFAULT_LNG = "126.9225";

    //지도 내 충전소 검색
    //@GetMapping("/map")
    //public List<ChargingStation> searchStation(@RequestParam double swLat, @RequestParam double neLat, @RequestParam double swLng, @RequestParam double neLng){
    //    return chargingStationService.findByLatBetweenAndLngBetween(swLat, neLat, swLng, neLng);
    //}

    //검색 조건
    @GetMapping("/map")
    @Operation(summary = "충전소 검색", description = "충전소 검색 충전기 타입(chgerType), 이용제한여부(limitYn), 충전중 제외(stat)의 조건을 충족하는 사각범위(넘서위도,북동위도,남서경도,북동경도)내 충전소를 검색합니다. 조건은 전부 필수 조건이 아님")
    public List<ChargerStationResponse> searchBaseStation(
            //충전 타입
            @RequestParam(value = "chgerType", required = false) List<String> chgerTypes,
            //이용제한 여유
            @RequestParam(value = "limitYn", required = false) String limitYn,
            //충전 가능 여부
            @RequestParam(value = "stat", required = false) String stat,
            //지도 내 영역
            @RequestParam double swLat, @RequestParam double neLat,
            @RequestParam double swLng, @RequestParam double neLng
    ) {
        return chargingStationService.searchBaseStation(chgerTypes, limitYn, stat, swLat, neLat, swLng, neLng);
    }

    //추천 수
    @PostMapping("/map/{statId}/like")
    @Operation(summary = "충전소 추천", description = "충전소를 추천합니다.")
    public ChargerStationResponse likeStation(@Parameter(name = "statId", description = "충전소의 id", in = ParameterIn.PATH)
                                                     @PathVariable String statId){
        return chargingStationService.addLike(statId);
    }

}
