package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.DTO.ChargerStationResponse;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.ChargingStationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChargingStationService {
    private final ChargingStationRepository chargingStationRepository;

    //조회용(충전소id 검색)
    public ChargingStation findById(String id){
        return chargingStationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("충전소를 찾을 수 없습니다."));
    }

    //조회용(모든 충전소 조회)
    public List<ChargingStation> findAll(){
        return chargingStationRepository.findAll();
    }

    //조회용(충전소 id 검색)
    public Optional<ChargingStation> findByOptional(String statId){
        return chargingStationRepository.findById(statId);
    }

    //DTO 변환예정
    //지도 내 충전소 조회
    public List<ChargingStation> findByLatBetweenAndLngBetween(double swLat, double neLat, double swLng, double neLng){
        List<ChargingStation> chargingStations = chargingStationRepository.findByLatBetweenAndLngBetween(swLat, neLat, swLng, neLng);
        return chargingStations;
    }

    //커스텀 충전소 조회
    public List<ChargerStationResponse> searchBaseStation(List<String> chgerTypes, String limitYn, String stat, double swLat, double neLat, double swLng, double neLng){
        List<ChargingStation> chargingStations = chargingStationRepository.findStationsByConditions(chgerTypes, limitYn, stat, swLat, neLat, swLng, neLng);
        List<ChargerStationResponse> result = new ArrayList<>();
        for (ChargingStation item : chargingStations){
            ChargerStationResponse chargerStationResponse = new ChargerStationResponse(item);
            result.add(chargerStationResponse);
        }
        return result;
    }

    //충전소 추천 수 반영
    public ChargerStationResponse addLike(String statId){
        ChargingStation chargingStation = chargingStationRepository.findById(statId)
                .orElseThrow(() -> new IllegalArgumentException("충전소를 찾을 수 없습니다."));
        chargingStation.setLikes(chargingStation.getLikes()+1);
        ChargerStationResponse chargerStationResponse = new ChargerStationResponse(chargingStationRepository.save(chargingStation));
        return chargerStationResponse;
    }
}
