package com.eatz.domain.restaurantUser;

import com.eatz.shared.domain.UserRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantUserRepository extends UserRepository<RestaurantUser> {
    Optional<RestaurantUser> findByEmail(String email);
    void delete(RestaurantUser user);
    boolean existsByEmailAndIsDeletedFalse(String email);
    List<RestaurantUser> findAll();
    Optional<String> findRoleByEmailAndIsDeletedFalse(String email);
}