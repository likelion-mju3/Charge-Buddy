package chargebuddy.example.chargebuddy.ChargingStationInfo.Config;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerApiForm;
import chargebuddy.example.chargebuddy.ChargingStationInfo.batch.ChargerApiBatchProcessor;
import chargebuddy.example.chargebuddy.ChargingStationInfo.batch.ChargerApiBatchReader;
import chargebuddy.example.chargebuddy.ChargingStationInfo.batch.ChargerApiBatchWriter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChargerBatchUpdateConfig {
    @Getter
    @Setter
    private boolean isBatchUpdateRunning = false;
    private final int CHUNK_SIZE = 1;

    @Bean
    public Job chargerBatchUpdateJob(JobRepository jopRepository, Step chargerApiBatchUpdateStep){
        return new JobBuilder("chargerApiBatchUpdateJob", jopRepository)
                .start(chargerApiBatchUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step chargerApiBatchUpdateStep(
            JobRepository jobRepository,
            ChargerApiBatchReader reader,
            ChargerApiBatchProcessor processor,
            ChargerApiBatchWriter writer,
            PlatformTransactionManager manager
    ) {
        return new StepBuilder("chargerApiBatchUpdateStep", jobRepository)
                .<List<ChargerApiForm>, List<ChargerApiForm>>chunk(CHUNK_SIZE, manager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
