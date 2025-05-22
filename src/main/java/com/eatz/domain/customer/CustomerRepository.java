package com.eatz.domain.customer;

import com.eatz.shared.domain.UserRepository;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends UserRepository<Customer> {
    Optional<Customer> findByIdAndIsDeletedFalse(Long id);
    Optional<Customer> findByEmailAndIsDeletedTrue(String email);
    List<Customer> findAll();
}