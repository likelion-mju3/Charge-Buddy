package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import chargebuddy.example.chargebuddy.jpa.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseTime {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
    private ChargingStation chargingStation;

    private String userName;

    private String password;

    @Setter
    private int likes = 0;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String reviewText;

    private LocalDateTime reviewTime;
}
