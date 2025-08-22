package chargebuddy.example.chargebuddy.Dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "리뷰 응답")
public class ReviewResponse {

    @Schema(description = "리뷰 ID", example = "1001")
    private Long id;

    @Schema(description = "충전소 placeId/외부ID", example = "KAKAO_xxx 또는 공공 statId")
    private String placeId;

    @Schema(description = "충전소명(옵션)", example = "서울시청 주차장")
    private String stationName;

    @Schema(description = "작성자명", example = "홍길동")
    private String userName;

    @Schema(description = "추천수", example = "12")
    private int likes;

    @Schema(description = "리뷰 본문", example = "충전 속도 빠르고 주차 편해요")
    private String reviewText;

    @Schema(description = "작성일시", example = "2025-01-14T09:30:00")
    private LocalDateTime reviewTime;
}