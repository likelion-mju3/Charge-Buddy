package chargebuddy.example.chargebuddy.ChargingStationInfo.scheduler;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Config.ChargerBatchUpdateConfig;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargerBatchUpdateService;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChargerDataInitializer implements ApplicationRunner {
    private final ChargerBatchUpdateService chargerBatchUpdateService;
    private final ChargerService chargerService;
    private final ChargerBatchUpdateConfig chargerBatchUpdateConfig;

    @Override
    public void run(ApplicationArguments args) {
        log.info("[ApplicationRunner] : 서버 시작 후 초기 데이터 로드");
        chargerBatchUpdateConfig.setBatchUpdateRunning(true);

        chargerBatchUpdateService.runChargerBatchUpdateJob();
        chargerService.deleteAllDeletedChargers();

        log.info("[ApplicationRunner] : 초기 데이터 로드 완료");
        chargerBatchUpdateConfig.setBatchUpdateRunning(false);
    }
}
