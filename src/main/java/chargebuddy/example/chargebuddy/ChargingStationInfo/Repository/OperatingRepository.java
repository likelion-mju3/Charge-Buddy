package chargebuddy.example.chargebuddy.ChargingStationInfo.Repository;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Operating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperatingRepository extends JpaRepository<Operating, String> {

    List<Operating> findByIsPrimary(String isPrimary);

    Optional<Operating> findByBnm(String bnm);
}
