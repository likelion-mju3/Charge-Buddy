package chargebuddy.example.chargebuddy.Repository;

import chargebuddy.example.chargebuddy.Domain.StationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface StationReviewRepository extends JpaRepository<StationReview, Long> {


    List<StationReview> findByPlaceId(String placeId);

    // 중복 체크 (placeId + userName + reviewText 기준)
    Optional<StationReview> findByPlaceIdAndUserNameAndReviewText(
            String placeId, String userName, String reviewText
    );


    // 새 리뷰 판별용
    int countByPlaceId(String placeId);

    @Query("select max(r.reviewTime) from StationReview r where r.placeId = :placeId")
    LocalDateTime findLastReviewTime(String placeId);

    // 요약 입력용으로 최근 N개만 가져오자 (토큰/길이 절약)
    List<StationReview> findTop5ByPlaceIdOrderByReviewTimeDesc(String placeId);



}
