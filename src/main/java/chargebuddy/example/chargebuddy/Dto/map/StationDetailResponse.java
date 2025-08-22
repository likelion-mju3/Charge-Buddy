package chargebuddy.example.chargebuddy.Dto.map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "충전소 상세 응답")
public class StationDetailResponse {

    @Schema(description = "충전소 메타")
    private StationMarker station;

    @Schema(description = "개별 충전기 상태")
    private List<ChargerStatusDto> chargers;

    // 리뷰는 팀 컨트롤러에서 엔티티 그대로 반환 중이므로
    // 필요 시 ReviewResponse 리스트를 추가
    // private List<ReviewResponse> reviews;
}