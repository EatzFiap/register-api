package com.eatz.infrastructure.persistence.repository;

import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.infrastructure.persistence.entity.RestaurantUserTypeEntity;
import com.eatz.infrastructure.persistence.mapper.RestaurantUserTypeEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RestaurantUserTypeRepositoryImpl implements RestaurantUserTypeRepository {
    private final JpaRestaurantUserTypeRepository jpaRepository;

    public RestaurantUserTypeRepositoryImpl(JpaRestaurantUserTypeRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RestaurantUserType save(RestaurantUserType restaurantUserType) {
        RestaurantUserTypeEntity entity = RestaurantUserTypeEntityMapper.toEntity(restaurantUserType);
        RestaurantUserTypeEntity savedEntity = jpaRepository.save(entity);
        return RestaurantUserTypeEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RestaurantUserType> findById(Long id) {
        return jpaRepository.findById(id)
                .map(RestaurantUserTypeEntityMapper::toDomain);
    }

    @Override
    public List<RestaurantUserType> findAll() {
        return jpaRepository.findByIsDeletedFalse()
                .stream()
                .map(RestaurantUserTypeEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Optional<RestaurantUserTypeEntity> entityOpt = jpaRepository.findById(id);
        if (entityOpt.isPresent()) {
            RestaurantUserTypeEntity entity = entityOpt.get();
            entity.setDeleted(true);
            jpaRepository.save(entity);
        }
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByNameAndIsDeletedFalse(name);
    }
} 