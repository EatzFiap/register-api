package com.eatz.infrastructure.persistence.repository;

import com.eatz.infrastructure.persistence.entity.RestaurantUserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRestaurantUserTypeRepository extends JpaRepository<RestaurantUserTypeEntity, Long> {
    List<RestaurantUserTypeEntity> findByIsDeletedFalse();
    boolean existsByNameAndIsDeletedFalse(String name);
} 