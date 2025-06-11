package com.errabi.review.repository;

import com.errabi.review.domain.Review;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReviewRepository extends ReactiveMongoRepository<Review, String> {
    Flux<Review> findByProductId(String productId);
    Flux<Review> findByUserId(String userId);
}
