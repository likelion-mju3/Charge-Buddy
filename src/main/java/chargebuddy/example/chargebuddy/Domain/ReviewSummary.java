package chargebuddy.example.chargebuddy.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReviewSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placeId;       // 구글 Place ID


    @Column(columnDefinition = "TEXT")        // 요약은 길 수 있음
    private String summaryText;

    private LocalDateTime createdAt;     // 저장 시각 (문자열 or LocalDateTime)

}
