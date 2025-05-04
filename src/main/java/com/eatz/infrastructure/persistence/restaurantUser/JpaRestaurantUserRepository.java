package com.eatz.infrastructure.persistence.restaurantUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRestaurantUserRepository extends JpaRepository<RestaurantUserEntity, Long> {
    Optional<RestaurantUserEntity> findByEmail(String email);
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<RestaurantUserEntity> findByIdAndIsDeletedFalse(Long id);
    List<RestaurantUserEntity> findAllByIsDeletedFalse();
    Optional<String> findRoleByEmailAndIsDeletedFalse(String email);
}