package com.eatz.infrastructure.persistence.restaurantUser;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantUserRepositoryImpl implements RestaurantUserRepository {

    private final JpaRestaurantUserRepository jpaRepository;

    public RestaurantUserRepositoryImpl(JpaRestaurantUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RestaurantUser save(RestaurantUser user) {
        RestaurantUserEntity entity = RestaurantUserEntityMapper.toEntity(user);
        RestaurantUserEntity saved = jpaRepository.save(entity);
        return RestaurantUserEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<RestaurantUser> findById(Long id) {
        return jpaRepository.findById(id)
                .map(RestaurantUserEntityMapper::toDomain);
    }

    @Override
    public Optional<RestaurantUser> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(RestaurantUserEntityMapper::toDomain);
    }

    @Override
    public void delete(RestaurantUser user) {
        jpaRepository.delete(RestaurantUserEntityMapper.toEntity(user));
    }

    @Override
    public boolean existsByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.existsByEmailAndIsDeletedFalse(email);
    }

    @Override
    public Optional<RestaurantUser> findByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.findByEmailAndIsDeletedFalse(email)
                .map(RestaurantUserEntityMapper::toDomain);
    }

    @Override
    public List<RestaurantUser> findAll() {
        return jpaRepository.findAllByIsDeletedFalse().stream()
                .map(RestaurantUserEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<String> findRoleByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.findRoleByEmailAndIsDeletedFalse(email);
    }
}