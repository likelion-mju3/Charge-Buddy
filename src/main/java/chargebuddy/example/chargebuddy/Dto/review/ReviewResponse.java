package chargebuddy.example.chargebuddy.Dto.review;


import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.*;

@Getter @Setter
@Schema(description = "리뷰 응답 DTO")
public class ReviewResponse {

    @Schema(description = "리뷰 ID", example = "demo")
    private String reviewId;

    @Schema(description = "대상 타입(STATION/PLACE)", example = "STATION")
    private String targetType;
    @Schema(description = "대상 ID", example = "ST123456")
    private String targetId;

    @Schema(description = "작성자 ID", example = "USER-1001")
    private String userId;

    @Schema(description = "리뷰 본문", example = "주차가 편하고 충전이 빨라서 자주 사용해요.")
    private String content;

    @Schema(description = "좋아요 수", example = "15")
    private Integer likes;

    @Schema(description = "작성 일시",
            example = "2025-08-20T12:34:56")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}