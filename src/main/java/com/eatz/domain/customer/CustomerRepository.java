package com.eatz.domain.customer;

import com.eatz.shared.domain.UserRepository;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<Customer> findByEmailAndIsDeletedFalse(String email);
    List<Customer> findAll();
}