package chargebuddy.example.chargebuddy.Repository;


import chargebuddy.example.chargebuddy.Domain.ReviewSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewSummaryRepository extends JpaRepository<ReviewSummary, Long> {
    Optional<ReviewSummary> findByPlaceId(String placeId);
}
