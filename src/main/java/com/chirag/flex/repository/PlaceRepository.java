package com.chirag.flex.repository;

import com.chirag.flex.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place,Long> {
    Optional<Place> findByName(String name);
}