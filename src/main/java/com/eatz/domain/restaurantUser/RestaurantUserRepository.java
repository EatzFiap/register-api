package com.eatz.domain.restaurantUser;

import java.util.List;
import java.util.Optional;

public interface RestaurantUserRepository {
    RestaurantUser save(RestaurantUser user);
    Optional<RestaurantUser> findById(Long id);
    Optional<RestaurantUser> findByEmail(String email);
    void delete(RestaurantUser user);
    boolean existsByEmailAndIsDeletedFalse(String email);
    List<RestaurantUser> findAll();
    Optional<String> findRoleByEmailAndIsDeletedFalse(String email);
}