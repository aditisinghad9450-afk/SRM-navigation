package com.srm.navigation.repository;

import com.srm.navigation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // JpaRepository gives us findAll(), save(), etc. for free
}