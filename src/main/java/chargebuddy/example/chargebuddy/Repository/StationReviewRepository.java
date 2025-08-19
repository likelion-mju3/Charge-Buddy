package chargebuddy.example.chargebuddy.Repository;

import chargebuddy.example.chargebuddy.Domain.StationReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StationReviewRepository extends JpaRepository<StationReview, Long> {


    List<StationReview> findByPlaceId(String placeId);

    // 중복 체크 (placeId + userName + reviewText 기준)
    Optional<StationReview> findByPlaceIdAndUserNameAndReviewText(
            String placeId, String userName, String reviewText
    );



}
