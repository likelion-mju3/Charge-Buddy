package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    private String zcode;

    @Column(unique = true)
    private String regionName;
}
