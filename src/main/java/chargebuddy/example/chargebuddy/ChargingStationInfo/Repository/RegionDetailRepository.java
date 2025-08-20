package chargebuddy.example.chargebuddy.ChargingStationInfo.Repository;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.RegionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionDetailRepository extends JpaRepository<RegionDetail, String> {
    List<RegionDetail> findByZcodeZcode(String zcode);
}
