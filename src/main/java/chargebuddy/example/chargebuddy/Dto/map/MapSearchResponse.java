package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "지도 검색 응답(충전소/상권)")
public class MapSearchResponse {

    @Schema(description = "충전소 마커(경량) 목록")
    @NotNull
    private List<StationMarkerSummary> stations;

    @Schema(description = "상권 마커 목록(상권 탭일 때만 채움, 기본 빈 배열)")
    @NotNull
    private List<PlaceMarker> places;
}