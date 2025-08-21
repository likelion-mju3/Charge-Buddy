package chargebuddy.example.chargebuddy.ChargingStationInfo.batch;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerApiForm;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Charger;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Operating;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.RegionDetail;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.ChargerRepository;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.ChargingStationRepository;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.OperatingRepository;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.RegionDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class ChargerApiBatchWriter implements ItemWriter<List<ChargerApiForm>> {
    private final RegionDetailRepository regionDetailRepository;
    private final OperatingRepository operatingRepository;
    private final ChargingStationRepository chargingStationRepository;
    private final ChargerRepository chargerRepository;
    @Override
    public void write(Chunk<? extends List<ChargerApiForm>> chunk) {
        int insertStationCnt = 0, updateStationCnt = 0, insertChargerCnt = 0, updateChargerCnt = 0;

        Map<String, RegionDetail> regionMap = regionDetailRepository.findAll().stream()
                .collect(Collectors.toMap(RegionDetail::getZscode, region -> region));
        Map<String, Operating> companyMap = operatingRepository.findAll().stream()
                .collect(Collectors.toMap(Operating::getBusiId, company -> company));

        List<ChargingStation> updatingStationList = new LinkedList<>();
        List<Charger> updatingChargerList = new LinkedList<>();

        try {
            for (List<ChargerApiForm> items : chunk) {
                if (items.isEmpty()) continue;

                for (ChargerApiForm item : items) {
                    RegionDetail region = regionMap.get(item.getZscode());
                    Operating company = companyMap.get(item.getBusiId());
                    if (region == null || company == null) continue;

                    // 충전소 upsert
                    ChargingStation station = chargingStationRepository.findById(item.getStatId()).orElse(null);
                    if (station != null) {
                        station.update(item, company, region);
                        updatingStationList.add(station);
                        updateStationCnt++;
                    } else {
                        station = new ChargingStation(item, company, region);

                        chargingStationRepository.saveAndFlush(station);
                        insertStationCnt++;
                    }

                    // 충전기 upsert
                    Charger charger = chargerRepository.findByChargingStationAndChgerId(station, item.getChgerId())
                            .orElse(null);
                    if (charger != null) {
                        charger.update(item, station);
                        updateChargerCnt++;
                    } else {
                        charger = new Charger(item, station);
                        insertChargerCnt++;
                    }
                    updatingChargerList.add(charger);
                }
            }

            if (!updatingStationList.isEmpty()) {
                chargingStationRepository.saveAll(updatingStationList);
                log.info("[Batch] : 충전소 데이터 신규 {}건, 업데이트 {}건 저장 완료", insertStationCnt, updateStationCnt);
            }
            if (!updatingStationList.isEmpty()) {
                chargerRepository.saveAll(updatingChargerList);
                log.info("[Batch] : 충전기 데이터 신규 {}건, 업데이트 {}건 저장 완료", insertChargerCnt, updateChargerCnt);
            }
        } catch (Exception e) {
            log.error("[ERROR] : Batch 처리 중 오류 발생, {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
