package com.eatz.application.address.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.AddressRepository;

public class DeleteAddressUseCase {

    private final AddressRepository addressRepository;

    public DeleteAddressUseCase(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address execute(Address address) {
        if (address == null)
            throw new IllegalArgumentException("Endereço não pode ser nulo.");

        address.setDeleted(true);
        return addressRepository.save(address);
    }
}