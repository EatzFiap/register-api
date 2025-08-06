package com.eatz.infrastructure.persistence.restaurantUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaRestaurantUserRepository extends JpaRepository<RestaurantUserEntity, Long> {

    Optional<RestaurantUserEntity> findByEmail(String email);

    boolean existsByEmailAndIsDeletedFalse(String email);

    Optional<RestaurantUserEntity> findByIdAndIsDeletedFalse(Long id);

    List<RestaurantUserEntity> findAllByIsDeletedFalse();

    @Query("SELECT r.role FROM RestaurantUserEntity r WHERE r.email = :email AND r.isDeleted = false")
    Optional<String> findRoleByEmailAndIsDeletedFalse(@Param("email") String email);

    Optional<RestaurantUserEntity> findByEmailAndIsDeletedFalse(String email);
    
    boolean existsByRestaurantUserTypeIdAndIsDeletedFalse(Long restaurantUserTypeId);
}