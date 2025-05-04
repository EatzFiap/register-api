package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaRepository;
    private final CustomerEntityMapper mapper;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaRepository, CustomerEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);
        CustomerEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.existsByEmailAndIsDeletedFalse(email);
    }

    @Override
    public Optional<Customer> findByEmailAndIsDeletedTrue(String email) {
        return jpaRepository.findByEmailAndIsDeletedTrue(email)
                .map(mapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}