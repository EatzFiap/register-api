package com.eatz.domain.restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {
    Optional<Restaurant> findById(UUID id);
    Optional<Restaurant> findByEmail(String email);
    Optional<Restaurant> findByLogin(String login);
    Restaurant save(Restaurant restaurant);
    void delete(Restaurant restaurant);
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<Restaurant> findByIdAndAtivoTrue(UUID id);
    Optional<Restaurant> findByEmailAndAtivoTrue(String email);
    List<Restaurant> findAll();
}