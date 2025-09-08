package com.gap.learning.gapservice.repository;

import com.gap.learning.gapservice.document.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    List<Cart> findAllByUserId(String userId);

    void deleteByUserId(String userId);
}
