package com.eatz.infrastructure.persistence.address;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.AddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final JpaAddressRepository jpaRepository;

    public AddressRepositoryImpl(
            JpaAddressRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Address save(Address address) {
        AddressEntity entity = AddressEntityMapper.toEntity(address);
        AddressEntity saved = jpaRepository.save(entity);
        return AddressEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return jpaRepository.findById(id).map(AddressEntityMapper::toDomain);
    }

    @Override
    public List<Address> findAll() {
        return jpaRepository.findAll().stream().map(AddressEntityMapper::toDomain).toList();
    }

    @Override
    public void delete(Address address) {
        jpaRepository.delete(AddressEntityMapper.toEntity(address));
    }
}