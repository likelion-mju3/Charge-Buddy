package chargebuddy.example.chargebuddy.ChargingStationInfo.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
public class Operating {
    //운영 기관id
    @Id
    private String busiId;
    //운영 기관명
    private String bnm;
    //점유율 상위 15개 운영기관 여부
    //private String isPrimary;

    @OneToMany(mappedBy = "operating")
    private List<ChargingStation> chargingStations = new ArrayList<>();
}
