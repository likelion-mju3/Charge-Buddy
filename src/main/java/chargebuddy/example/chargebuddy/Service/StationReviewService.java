package chargebuddy.example.chargebuddy.Service;


import chargebuddy.example.chargebuddy.Domain.StationReview;
import chargebuddy.example.chargebuddy.Repository.StationReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StationReviewService {

    private final StationReviewRepository reviewRepository;

    public StationReview create(
            String placeId,
            String userName,
            String password,       // 데모: 평문 (운영은 해시 권장)
            String reviewText,
            LocalDateTime reviewTime // null이면 now()
    ) {
        StationReview r = new StationReview();
        r.setPlaceId(placeId);
        r.setUserName(userName);
        r.setPassword(password);
        r.setReviewText(reviewText);
        r.setReviewTime(reviewTime != null ? reviewTime : LocalDateTime.now());
        return reviewRepository.save(r);
    }

    // READ (단건)
    @Transactional(readOnly = true)
    public List<StationReview> getByPlaceId(String placeId) {
        return reviewRepository.findByPlaceId(placeId);
    }


    // UPDATE (비번 확인, 수정할 값만 전달 가능)
    public StationReview update(
            Long id,
            String password,        // 본인확인
            Integer rating,         // null이면 변경없음
            String reviewText       // null이면 변경없음
    ) {
        StationReview r = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (r.getPassword() == null || !r.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (rating != null) r.setLikes(rating);
        if (reviewText != null) r.setReviewText(reviewText);

        return reviewRepository.save(r);
    }

    // DELETE (비번 확인)
    public void delete(Long id, String password) {
        StationReview r = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        if (r.getPassword() == null || !r.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        reviewRepository.delete(r);
    }

    @Transactional
    public StationReview addLike(Long reviewId) {
        StationReview r = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        r.setLikes(r.getLikes() + 1);
        return reviewRepository.save(r);
    }
}