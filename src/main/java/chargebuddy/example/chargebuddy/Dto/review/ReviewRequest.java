package chargebuddy.example.chargebuddy.Dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "리뷰 작성 요청 DTO")
public class ReviewRequest {

    @Schema(description = "대상 타입(STATION/PLACE)",
            example = "STATION",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "STATION|PLACE")
    private String targetType;

    @Schema(description = "대상 ID (충전소 ID 또는 상권 ID)",
            example = "ST123456",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String targetId;

    @Schema(description = "리뷰 작성자 ID",
            example = "USER-1001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String userId;

    @Schema(description = "리뷰 비밀번호 (수정/삭제 인증용)",
            example = "mypassword", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String password;

    @Schema(description = "리뷰 본문",
            example = "주차가 편하고 충전이 빨라서 자주 사용해요.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank @Size(min = 1, max = 500)
    private String content;
}