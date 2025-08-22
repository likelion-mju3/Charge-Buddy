package chargebuddy.example.chargebuddy.ChargingStationInfo.Repository;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r join r.chargingStation cs where cs.statId = :statId")
    List<Review> findByChargingStationStatId(String statId);
    @Query("SELECT r FROM Review r JOIN r.chargingStation cs WHERE cs.statId = :statId ORDER BY r.reviewTime DESC limit 5")
    List<Review> findTop5ByStatIdOrderByReviewTimeDesc(String statId);
}
