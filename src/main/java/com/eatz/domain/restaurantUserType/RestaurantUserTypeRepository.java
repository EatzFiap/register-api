package com.eatz.domain.restaurantUserType;

import java.util.List;
import java.util.Optional;

public interface RestaurantUserTypeRepository {
    RestaurantUserType save(RestaurantUserType restaurantUserType);
    Optional<RestaurantUserType> findById(Long id);
    List<RestaurantUserType> findAll();
    void delete(Long id);
    boolean existsByName(String name);
} 