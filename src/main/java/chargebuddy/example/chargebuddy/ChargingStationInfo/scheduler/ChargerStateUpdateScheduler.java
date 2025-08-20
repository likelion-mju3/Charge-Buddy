package chargebuddy.example.chargebuddy.ChargingStationInfo.scheduler;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Config.ChargerBatchUpdateConfig;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Config.ChargerStateUpdateConfig;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargerStateUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChargerStateUpdateScheduler {
    private final ChargerStateUpdateService chargerStateUpdateService;
    private final ChargerStateUpdateConfig chargerStateUpdateConfig;
    private final ChargerBatchUpdateConfig chargerBatchUpdateConfig;

    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void updateChargerStateScheduled(){
        if(chargerBatchUpdateConfig.isBatchUpdateRunning()) return;

        chargerStateUpdateConfig.setUpdateRunning(true);
        log.info("[Scheduler] : 충전기 상태 업데이트 시작");

        chargerStateUpdateService.updateChargerState(
                chargerStateUpdateConfig.getBaseUrl(),
                chargerStateUpdateConfig.getContentType(),
                chargerStateUpdateConfig.getNumOfRows(),
                chargerStateUpdateConfig.getPageNo(),
                chargerStateUpdateConfig.getPeriod());

        chargerStateUpdateConfig.setUpdateRunning(false);
        log.info("[Scheduler] : 충전기 상태 업데이트 종료");
    }
}
