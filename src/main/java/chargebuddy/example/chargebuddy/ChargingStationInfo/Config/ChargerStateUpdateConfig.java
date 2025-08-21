package chargebuddy.example.chargebuddy.ChargingStationInfo.Config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@RequiredArgsConstructor
public class ChargerStateUpdateConfig {

    @Setter
    private boolean isUpdateRunning = false;

    private final String baseUrl = "https://apis.data.go.kr/B552584/EvCharger/getChargerStatus";

    private final String contentType = "JSON";

    private final int numOfRows = 10000;

    private final int pageNo = 1;

    private final int period = 10;

    private final String zcode = "11";
}
