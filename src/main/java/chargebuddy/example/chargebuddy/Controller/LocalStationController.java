package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.LocalStation.LocalStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocalStationController {
    private final LocalStationService localStationService;

    @PostMapping("/map")
    public ResponseEntity<String> search(@RequestParam(value = "category", defaultValue = "") List<String> category,
                                         @RequestParam double swLat, @RequestParam double neLat,
                                         @RequestParam double swLng, @RequestParam double neLng){
        return localStationService.getSearch(category, swLat, neLat, swLng, neLng);
    }
}
