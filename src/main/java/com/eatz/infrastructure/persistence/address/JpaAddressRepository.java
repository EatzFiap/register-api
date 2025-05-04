package com.eatz.infrastructure.persistence.address;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaAddressRepository extends JpaRepository<AddressEntity, Long> {
}