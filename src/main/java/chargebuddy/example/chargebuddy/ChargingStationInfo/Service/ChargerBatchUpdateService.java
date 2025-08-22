package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChargerBatchUpdateService {
    private final JobLauncher jopLauncher;
    private final Job chargerApiBatchUpdateJob;

    public void runChargerBatchUpdateJob(){
        try {
            String execDate = LocalDateTime.now().toString();
            JobParameters parameters = new JobParametersBuilder()
                    .addString("execDate", execDate)
                    .toJobParameters();

            jopLauncher.run(chargerApiBatchUpdateJob, parameters);
        } catch (Exception e) {
            log.error("[ERROR] : 배치 업데이트 중 오류 발생, {}", e.getMessage());
        }
    }
}
