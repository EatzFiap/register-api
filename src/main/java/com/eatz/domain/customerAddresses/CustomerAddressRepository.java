package com.eatz.domain.customerAddresses;

import java.util.List;
import java.util.Optional;


public interface CustomerAddressRepository {
    CustomerAddress save(CustomerAddress customerAddress);
    Optional<CustomerAddress> findById(Long id);
    List<CustomerAddress> findByCustomerId(Long customerId);
    void delete(CustomerAddress customerAddress);
}