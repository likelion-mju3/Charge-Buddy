package chargebuddy.example.chargebuddy.ChargingStationInfo.Service;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerApiForm;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Charger;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Repository.ChargerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.ChannelOption;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chargebuddy.example.chargebuddy.AppConfig.objectMapper;
import static chargebuddy.example.chargebuddy.AppConfig.serviceKey;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ChargerService {
    private final ChargerRepository chargerRepository;

    //조회용
    public Charger findByChargingStationIdAndChgerId(ChargingStation chargingStation, String chgerId){
        return chargerRepository.findByChargingStationAndChgerId(chargingStation, chgerId)
                .orElseThrow(() -> new EntityNotFoundException("충전기를 찾을 수 없습니다.")
                );
    }

    //충전기 상태 변경 조회
    public HashMap webClientApiGetChargerStatus(
            String baseUrl, String serviceKey, int numOfRows, int pageNo, String type, int period, String zcode
    ){
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .queryParam("dataType", type)
                .queryParam("period", period)
                .queryParam("zcode", zcode)
                .build(true)
                .toUri();

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))  //5MB
                .baseUrl(uri.toString())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        HashMap hashMap = null;
        try {
            String response = webClient.get()
                    .uri(uri)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            hashMap = objectMapper.readValue(response, HashMap.class);
        } catch (JsonProcessingException e) {
            log.error("[ERROR] : OpenAPI 데이터 JSON 파싱 오류 {}", e.getMessage());
        } catch(Exception e) {
            log.error("[ERROR] : OpenAPI 데이터 불러오기 중 에러 발생) {}", e.getMessage());
        }
        return hashMap;
    }

    //충전기 정보 조회
    public List<ChargerApiForm> webClientApiGetChargerInfo(
            String baseUrl, int numOfRows, int pageNo, String dataType
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", numOfRows)
                .queryParam("pageNo", pageNo)
                .queryParam("zcode", "11")
                .queryParam("dataType", dataType)
                .build(true)
                .toUri();

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

        WebClient webClient = WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(5 * 1024 * 1024))  //5MB
                .baseUrl(uri.toString())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        HashMap apiDataMap = null;
        try {
            String response = webClient.get()
                    .uri(uri)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            apiDataMap = objectMapper.readValue(response, HashMap.class);
        } catch (JsonProcessingException e) {
            log.error("[ERROR] : OpenAPI 데이터 JSON 파싱 오류 {}", e.getMessage());
            return null;
        } catch(Exception e) {
            log.error("[ERROR] : OpenAPI 데이터 불러오기 중 에러 발생 {}", e.getMessage());
            return null;
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>)((Map<String, Object>)apiDataMap.get("items")).get("item");

        List<ChargerApiForm> list = items.stream()
                .map(item -> new ChargerApiForm(item, formatter)).toList();
        if (!list.isEmpty()) {
            log.info("[OpenAPI] : OpenAPI 데이터 {}건 불러오기 완료", list.size());
        }
        return list;
    }

    @Transactional
    public void deleteAllDeletedChargers() {
        Long beforeDeleteChargerCount = chargerRepository.count();
        chargerRepository.deleteAllDeletedChargers();
        Long afterDeleteChargerCount = chargerRepository.count();

        log.info("[DB] : OpenAPI상 삭제 충전기 총 {}건 삭제 완료 / 기존 {}건, 현재 {}건",
                beforeDeleteChargerCount - afterDeleteChargerCount, beforeDeleteChargerCount, afterDeleteChargerCount);
    }
}
