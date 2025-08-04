package com.eatz.domain.restaurant;

import java.util.Optional;
import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    List<Restaurant> findAll();
    void delete(Restaurant restaurant);

    boolean existsByNameAndIsDeletedFalse(String name);
}

