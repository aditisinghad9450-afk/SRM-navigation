package com.srm.navigation.repository;

import com.srm.navigation.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    // JpaRepository gives us findAll(), save(), etc. for free
}