package chargebuddy.example.chargebuddy.ChargingStationInfo.Repository;

import chargebuddy.example.chargebuddy.ChargingStationInfo.ChargerStateUpdateForm;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Charger;
import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
    List<Charger> findByChargingStationStatId(String statId);

    Optional<Charger> findByChargingStationAndChgerId(ChargingStation chargingStation, String chgerId);

    //충전기 삭제
    @Modifying
    @Query("DELETE FROM Charger c WHERE c.delYn = 'Y'")
    void deleteAllDeletedChargers();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Charger c " +
            "SET c.stat = :#{#charger.stat}, " +
            "    c.statUpdDt = :#{#charger.statUpdDt}, " +
            "    c.lastTsdt = :#{#charger.lastTsdt}, " +
            "    c.lastTedt = :#{#charger.lastTedt}, " +
            "    c.nowTsdt = :#{#charger.nowTsdt} " +
            "WHERE c.chgerId = :#{#charger.chgerId} AND c.chargingStation.statId = :#{#charger.statId}")
    int updateChargerState(ChargerStateUpdateForm charger);

}
