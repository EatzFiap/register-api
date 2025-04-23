
package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final JpaRestaurantRepository jpaRepository;
    private final RestaurantEntityMapper mapper;

    public RestaurantRepositoryImpl(JpaRestaurantRepository jpaRepository, RestaurantEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Restaurant> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Restaurant> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<Restaurant> findByLogin(String login) {
        return jpaRepository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(restaurant)));
    }

    @Override
    public void delete(Restaurant restaurant) {
        jpaRepository.delete(mapper.toEntity(restaurant));
    }

    @Override
    public boolean existsByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.existsByEmailAndIsActiveTrue(email);
    }

    @Override
    public Optional<Restaurant> findByIdAndAtivoTrue(UUID id) {
        return jpaRepository.findById(id)
                .filter(RestaurantEntity::isActive)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Restaurant> findByEmailAndAtivoTrue(String email) {
        RestaurantEntity restaurantEntity = jpaRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with email: " + email)
                );
        return Optional.of(mapper.toDomain(restaurantEntity));
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
