package chargebuddy.example.chargebuddy.ChargingStationInfo.batch;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerApiForm;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Service.ChargerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
@Slf4j
@RequiredArgsConstructor
public class ChargerApiBatchReader implements ItemReader<List<ChargerApiForm>> {

    private final ChargerService chargerService;
    private int currentPageNo = 1;
    private final int numOfRows = 1000;
    private final String baseUrl = "https://apis.data.go.kr/B552584/EvCharger/getChargerInfo";
    private final String dataType = "JSON";
    @Override
    public List<ChargerApiForm> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        List<ChargerApiForm> list = chargerService.webClientApiGetChargerInfo(
                baseUrl, numOfRows,currentPageNo, dataType);
        if(list == null){
            return List.of(new ChargerApiForm());
        }

        log.info("[Batch] : 현재 불러온 데이터 페이지 : {} 페이지", currentPageNo);

        if(list.isEmpty()){
            log.info("[Batch] : Step 단위 Reader 불러오기 종료, 총 페이지 : {} 페이지", currentPageNo);
            return null;
        }

        currentPageNo++;
        return list;
    }
}
