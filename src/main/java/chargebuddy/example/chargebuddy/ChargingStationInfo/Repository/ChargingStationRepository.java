package chargebuddy.example.chargebuddy.ChargingStationInfo.Repository;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.ChargingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, String> {

    //지도 내 충전소 검색
    List<ChargingStation> findByLatBetweenAndLngBetween(double swLat, double neLat, double swLng, double neLng);

    //복합 조건 검색
    @Query("SELECT DISTINCT cs FROM ChargingStation cs " +
            "JOIN cs.charger c WHERE " +
            "(:chargerTypes IS NULL OR c.chgerType IN :chargerTypes) AND " +
            "(:limitYn IS NULL OR cs.limitYn = :limitYn) AND " +
            "(:stat IS NULL OR c.stat = :stat) AND " +
            "cs.lat BETWEEN :swLat AND :neLat AND " +
            "cs.lng BETWEEN :swLng AND :neLng")
    List<ChargingStation> findStationsByConditions(
            @Param("chargerTypes") List<String> chargerTypes,
            @Param("limitYn") String limitYn,
            @Param("stat") String stat,
            @Param("swLat") Double swLat,
            @Param("neLat") Double neLat,
            @Param("swLng") Double swLng,
            @Param("neLng") Double neLng);
}
