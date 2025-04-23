package com.eatz.infrastructure.persistence.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaRestaurantRepository extends JpaRepository<RestaurantEntity, UUID> {
    Optional<RestaurantEntity> findByEmail(String email);
    Optional<RestaurantEntity> findByLogin(String login);
    boolean existsByEmailAndIsActiveTrue(String email);
    Optional<RestaurantEntity> findByEmailAndIsActiveTrue(String email);
}
