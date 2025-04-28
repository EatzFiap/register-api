package com.eatz.domain.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findById(UUID id);
    Optional<Customer> findByEmail(String email);
    Customer save(Customer customer);
    void delete(Customer customer);
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<Customer> findByEmailAndAtivoTrue(String email);
    List<Customer> findAll();
}