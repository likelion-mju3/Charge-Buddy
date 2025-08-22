package chargebuddy.example.chargebuddy.ChargingStationInfo.DTO;

import chargebuddy.example.chargebuddy.ChargingStationInfo.Domain.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {
    private final Long id;
    private final String userName;
    private final String chargingStationId;
    private final LocalDateTime reviewTime;
    private final int likes;
    private final String reviewText;

    public ReviewResponse(Review review){
        this.id = review.getId();
        this.chargingStationId = review.getChargingStation().getStatId();
        this.userName = review.getUserName();
        this.reviewText = review.getReviewText();
        this.reviewTime = review.getReviewTime();
        this.likes = review.getLikes();
    }
}
