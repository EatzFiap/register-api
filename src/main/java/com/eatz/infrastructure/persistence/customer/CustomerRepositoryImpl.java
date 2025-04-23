
package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaRepository;
    private final CustomerEntityMapper mapper;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaRepository, CustomerEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<Customer> findByLogin(String login) {
        return jpaRepository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public Customer save(Customer customer) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(customer)));
    }

    @Override
    public void delete(Customer customer) {
        jpaRepository.delete(mapper.toEntity(customer));
    }

    @Override
    public boolean existsByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.existsByEmailAndIsActiveTrue(email);
    }

    @Override
    public Optional<Customer> findByIdAndAtivoTrue(UUID id) {
        return jpaRepository.findById(id)
                .filter(CustomerEntity::isActive)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmailAndAtivoTrue(String email) {
        CustomerEntity customerEntity = jpaRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with email: " + email)
                );
        return Optional.of(mapper.toDomain(customerEntity));
    }

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
