package chargebuddy.example.chargebuddy.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationReview {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String placeId; // google place id
    private String stationName; // 충전소 이름


    private String userName;
    private String password;

    @Builder.Default
    @Column(nullable = false)
    private int likes = 0; // 추천수


    @Column(columnDefinition = "TEXT")
    private String reviewText;


    private LocalDateTime reviewTime;

}
