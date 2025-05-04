package com.eatz.application.address.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.AddressRepository;

public class CreateAddressUseCase {

    private final AddressRepository addressRepository;

    public CreateAddressUseCase(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address execute(Address address) {
        if (address == null)
            throw new IllegalArgumentException("Endereço não pode ser nulo.");

        return addressRepository.save(address);
    }
}