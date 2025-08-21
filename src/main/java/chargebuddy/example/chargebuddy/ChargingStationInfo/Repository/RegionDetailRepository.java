package chargebuddy.example.chargebuddy.ChargingStationInfo.Repository;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.RegionDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegionDetailRepository extends JpaRepository<RegionDetail, String> {

}
