
package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.domain.restaurantuser.RestaurantUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RestaurantUserRepositoryImpl implements RestaurantUserRepository {

    private final JpaRestaurantRepository jpaRepository;
    private final RestaurantEntityMapper mapper;

    public RestaurantUserRepositoryImpl(JpaRestaurantRepository jpaRepository, RestaurantEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<RestaurantUser> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<RestaurantUser> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<RestaurantUser> findByLogin(String login) {
        return jpaRepository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public RestaurantUser save(RestaurantUser restaurantUser) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(restaurantUser)));
    }

    @Override
    public void delete(RestaurantUser restaurantUser) {
        jpaRepository.delete(mapper.toEntity(restaurantUser));
    }

    @Override
    public boolean existsByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.existsByEmailAndIsActiveTrue(email);
    }

    @Override
    public Optional<RestaurantUser> findByIdAndAtivoTrue(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<RestaurantUser> findByEmailAndAtivoTrue(String email) {
        RestaurantEntity restaurantEntity = jpaRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with email: " + email)
                );
        return Optional.of(mapper.toDomain(restaurantEntity));
    }

    @Override
    public List<RestaurantUser> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
