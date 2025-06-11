package com.errabi.review.service;

import com.errabi.review.domain.Review;
import com.errabi.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Mono<Review> saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Flux<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Flux<Review> getReviewsByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    public Flux<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }
}
