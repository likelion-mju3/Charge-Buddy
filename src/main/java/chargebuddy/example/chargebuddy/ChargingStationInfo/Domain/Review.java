package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
    private ChargingStation chargingStation;

    private String userName;

    private String password;

    private int likes = 0;

    @Column(columnDefinition = "TEXT")
    private String reviewText;

    private LocalDateTime reviewTime;
}
