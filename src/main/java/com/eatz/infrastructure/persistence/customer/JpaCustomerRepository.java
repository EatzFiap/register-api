package com.eatz.infrastructure.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<CustomerEntity> findByEmailAndIsDeletedFalse(String email);
    Optional<CustomerEntity> findByEmailAndIsDeletedTrue(String email);
    Optional<CustomerEntity> findByIdAndIsDeletedFalse(Long id);
}
