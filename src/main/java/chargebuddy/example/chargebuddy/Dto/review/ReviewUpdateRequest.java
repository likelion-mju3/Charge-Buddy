package chargebuddy.example.chargebuddy.Dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "리뷰 수정 요청 바디")
public class ReviewUpdateRequest {

    @Schema(description = "비밀번호(본인확인용)", example = "1234")
    @NotBlank
    @JsonProperty("비밀번호")
    private String password;

    @Schema(description = "수정할 리뷰 본문", example = "대기시간이 조금 줄었어요")
    @NotBlank
    @Size(max = 1000)
    @JsonProperty("리뷰")
    private String reviewText;
}