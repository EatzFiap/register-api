package com.eatz.infrastructure.persistence.customerAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface JpaCustomerAddressRepository extends JpaRepository<CustomerAddressEntity, Long> {
    List<CustomerAddressEntity> findByCustomerIdAndIsDeletedFalse(Long customerId);
}