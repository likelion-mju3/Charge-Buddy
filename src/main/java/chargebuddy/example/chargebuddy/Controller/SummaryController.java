package chargebuddy.example.chargebuddy.Controller;

import chargebuddy.example.chargebuddy.Domain.ReviewSummary;
import chargebuddy.example.chargebuddy.Domain.StationReview;
import chargebuddy.example.chargebuddy.Repository.StationReviewRepository;
import chargebuddy.example.chargebuddy.Service.ReviewSummaryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/summary")
public class SummaryController {

    private final ReviewSummaryService reviewSummaryService;
    private final StationReviewRepository stationReviewRepository;



    // 리뷰요약
    @PostMapping("/{placeId}")
    public ResponseEntity<ReviewSummary> summarizePlace(
            @PathVariable String placeId) {

        List<StationReview> reviews = stationReviewRepository.findByPlaceId(placeId);
        if (reviews.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        List<String> reviewTexts = reviews.stream()
                .map(StationReview::getReviewText)
                .collect(Collectors.toList());

        ReviewSummary summary = reviewSummaryService.summarizeAndSave(placeId, reviewTexts);

        return ResponseEntity.ok(summary);
    }



    @GetMapping("/{placeId}")
    public ResponseEntity<ReviewSummary> getSummary(@PathVariable String placeId) {
        return reviewSummaryService.findByPlaceId(placeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}