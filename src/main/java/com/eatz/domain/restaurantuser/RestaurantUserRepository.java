package com.eatz.domain.restaurantuser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantUserRepository {
    Optional<RestaurantUser> findById(UUID id);
    Optional<RestaurantUser> findByEmail(String email);
    Optional<RestaurantUser> findByLogin(String login);
    RestaurantUser save(RestaurantUser restaurantUser);
    void delete(RestaurantUser restaurantUser);
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<RestaurantUser> findByIdAndAtivoTrue(UUID id);
    Optional<RestaurantUser> findByEmailAndAtivoTrue(String email);
    List<RestaurantUser> findAll();
}