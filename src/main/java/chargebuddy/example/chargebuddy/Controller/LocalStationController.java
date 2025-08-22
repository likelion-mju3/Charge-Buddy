package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.LocalStation.LocalStationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "LocalStationAPI", description = "상권 API")
public class LocalStationController {
    private final LocalStationService localStationService;

    @GetMapping("/map/local")
    @Operation(summary = "상권검색 API", description = "상권 검색 장소 카테고리(category, 필수, List 아님-한번에 하나의 카테고리 검색-)에 해당되는 사각범위(넘서위도,북동위도,남서경도,북동경도)내 장소를 검색합니다.")
    public ResponseEntity<String> search(@RequestParam(value = "category") String category,
                                         @RequestParam double swLat, @RequestParam double neLat,
                                         @RequestParam double swLng, @RequestParam double neLng){
        return localStationService.getSearch(category, swLat, neLat, swLng, neLng);
    }
}
