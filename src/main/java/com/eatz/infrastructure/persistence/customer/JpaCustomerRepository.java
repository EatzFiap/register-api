package com.eatz.infrastructure.persistence.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByEmail(String email);
    Optional<CustomerEntity> findByLogin(String login);
    boolean existsByEmailAndIsActiveTrue(String email);
    Optional<CustomerEntity> findByEmailAndIsActiveTrue(String email);
}
