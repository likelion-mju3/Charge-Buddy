package chargebuddy.example.chargebuddy.ChargingStationInfo.scheduler;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Config.ChargerBatchUpdateConfig;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Config.ChargerStateUpdateConfig;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargerBatchUpdateService;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChargerBatchUpdateScheduler {
    private final ChargerBatchUpdateService chargerBatchUpdateService;
    private final ChargerBatchUpdateConfig chargerBatchUpdateConfig;
    private final ChargerStateUpdateConfig chargerStateUpdateConfig;
    private final ChargerService chargerService;

    @Scheduled(cron = "0 30 3 * * *")
    public void chargerBatchUpdateScheduled(){
        while (chargerStateUpdateConfig.isUpdateRunning()){
            try {
                Thread.sleep(1000);
                log.info("[Scheduler] : 데이터 배치 전역 업데이트 대기 중");
            } catch (InterruptedException e){
                log.error("[ERROR] : 데이터 배치 업데이트 대기 중 스레드 오류, 스레드 재설정");
                Thread.currentThread().interrupt();
            }
        }
        chargerBatchUpdateConfig.setBatchUpdateRunning(true);
        log.info("[Scheduler] : 데이터 전역 업데이트 시작");

        //배치 업데이트
        chargerBatchUpdateService.runChargerBatchUpdateJob();
        //delYn Y 삭제
        chargerService.deleteAllDeletedChargers();

        chargerBatchUpdateConfig.setBatchUpdateRunning(false);
        log.info("[Scheduler] : 데이터 업데이트 완료");

    }
}
