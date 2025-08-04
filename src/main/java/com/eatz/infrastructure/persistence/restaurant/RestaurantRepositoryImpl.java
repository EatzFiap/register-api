package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final JpaRestaurantRepository jpaRepository;

    public RestaurantRepositoryImpl(JpaRestaurantRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return RestaurantEntityMapper.toDomain(
                jpaRepository.save(RestaurantEntityMapper.toEntity(restaurant))
        );
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return jpaRepository.findById(id).map(RestaurantEntityMapper::toDomain);
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll().stream().map(RestaurantEntityMapper::toDomain).toList();
    }

    @Override
    public void delete(Restaurant restaurant) {
        jpaRepository.delete(RestaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public boolean existsByNameAndIsDeletedFalse(String name) {
        return jpaRepository.existsByNameAndIsDeletedFalse(name);
    }

}
