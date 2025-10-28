package com.srm.navigation.repository;

import com.srm.navigation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA automatically creates this query for us
    Optional<User> findByEmail(String email);
}