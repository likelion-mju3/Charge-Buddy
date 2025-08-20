package chargebuddy.example.chargebuddy.LocalStation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static chargebuddy.example.chargebuddy.AppConfig.localKey;

@Service
@RequiredArgsConstructor
public class LocalStationService {
    public ResponseEntity<String> getSearch(List<String> category, double swLat, double neLat, double swLng, double neLng) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK "+localKey);

        HttpEntity<String>entity = new HttpEntity<>("", headers);

        String baseUrl = "https://dapi.kakao.com/v2/local/search/category.json?"+
                "category_group_code=" + category +
                "&page=" + 45 +
                "&size=" + 15 +
                "&rect=" + swLng +"," + swLat + "," + neLng + "," + neLat;

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
    }

}
