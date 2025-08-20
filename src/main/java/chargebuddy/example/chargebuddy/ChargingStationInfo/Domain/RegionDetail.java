package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionDetail {
    @Id
    private String zscode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zcode")
    private Region zcode;

    private String regionDetailName;

    @OneToMany(mappedBy = "regionDetail")
    private List<ChargingStation> chargingStations = new ArrayList<>();
}
