package com.eatz.infrastructure.persistence.customerAddress;

import com.eatz.domain.customerAddresses.CustomerAddress;
import com.eatz.domain.customerAddresses.CustomerAddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CustomerAddressRepositoryImpl implements CustomerAddressRepository {

    private final JpaCustomerAddressRepository jpaRepository;

    public CustomerAddressRepositoryImpl(JpaCustomerAddressRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CustomerAddress save(CustomerAddress customerAddress) {
        CustomerAddressEntity entity = CustomerAddressEntityMapper.toEntity(customerAddress);
        CustomerAddressEntity saved = jpaRepository.save(entity);
        return CustomerAddressEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<CustomerAddress> findById(Long id) {
        return jpaRepository.findById(id).map(CustomerAddressEntityMapper::toDomain);
    }

    @Override
    public List<CustomerAddress> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerIdAndIsDeletedFalse(customerId)
                .stream().map(CustomerAddressEntityMapper::toDomain).toList();
    }

    @Override
    public void delete(CustomerAddress customerAddress) {
        jpaRepository.delete(CustomerAddressEntityMapper.toEntity(customerAddress));
    }
}