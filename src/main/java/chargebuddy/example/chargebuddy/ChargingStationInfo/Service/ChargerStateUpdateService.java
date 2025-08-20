package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerStateUpdateForm;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.ChargerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static chargebuddy.example.chargebuddy.AppConfig.serviceKey;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChargerStateUpdateService {
    private final ChargerService chargerService;
    private final ChargerRepository chargerRepository;

    @Async
    @Transactional
    public void updateChargerState(String baseUrl, String contentType, int numOfRows, int pageNo, int priod) {
        log.info("[OpenAPI] : OpenAPI 데이터 불러오기 시작");

        HashMap apiDataMap = chargerService.webClientApiGetChargerStatus(
                baseUrl, serviceKey, numOfRows, pageNo, contentType, priod);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        List<Map<String, Object>> items =
                (List<Map<String, Object>>)((Map<String, Object>)apiDataMap.get("items")).get("item");

        if (!items.isEmpty()) {
            log.info("[OpenAPI] : OpenAPI 데이터 {}건 불러오기 완료", items.size());
        }

        List<ChargerStateUpdateForm> apiChargerList =
                items.stream().map(item -> {
                    String statId = (String)item.get("statId");
                    String chgerId = String.valueOf(Integer.parseInt((String)item.get("chgerId"))); // 0 절삭
                    String stat = (String)item.get("stat");

                    LocalDateTime statUpdDt = Optional.ofNullable((String)item.get("statUpdDt"))
                            .filter(s -> !s.isEmpty())
                            .map(s -> LocalDateTime.parse(s, formatter))
                            .orElse(null);
                    LocalDateTime lastTsdt = Optional.ofNullable((String)item.get("lastTsdt"))
                            .filter(s -> !s.isEmpty())
                            .map(s -> LocalDateTime.parse(s, formatter))
                            .orElse(null);
                    LocalDateTime lastTedt = Optional.ofNullable((String)item.get("lastTedt"))
                            .filter(s -> !s.isEmpty())
                            .map(s -> LocalDateTime.parse(s, formatter))
                            .orElse(null);
                    LocalDateTime nowTsdt = Optional.ofNullable((String)item.get("nowTsdt"))
                            .filter(s -> !s.isEmpty())
                            .map(s -> LocalDateTime.parse(s, formatter))
                            .orElse(null);

                    return new ChargerStateUpdateForm(statId, chgerId, stat, statUpdDt, lastTsdt, lastTedt, nowTsdt);
                }).toList();

        // DB 업데이트  단건 업데이트
        AtomicInteger successCnt = new AtomicInteger();
        apiChargerList.forEach(charger -> {
            int isUpdated = chargerRepository.updateChargerState(charger);
            if (isUpdated == 1)
                successCnt.getAndIncrement();
        });
        log.info("[DB] : 충전기 상태 {}건 중 {}건 업데이트 완료", apiChargerList.size(), successCnt);
    }

}
