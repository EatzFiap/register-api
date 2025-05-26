package com.eatz.application.customerAddresses.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.customerAddresses.CustomerAddress;
import com.eatz.domain.customerAddresses.CustomerAddressRepository;

public class AssociateAddressToCustomerUseCase {

    private final CustomerAddressRepository customerAddressRepository;

    public AssociateAddressToCustomerUseCase(CustomerAddressRepository customerAddressRepository) {
        this.customerAddressRepository = customerAddressRepository;
    }

    public CustomerAddress execute(
            Long customerId,
            Address address,
            String nickname,
            boolean isDefault
    ) {
        if(customerId == null)
            throw new IllegalArgumentException("ID do cliente não pode ser nulo.");

        if(address.getId() == null)
            throw new IllegalArgumentException("ID do endereço não pode ser nulo.");

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerId(customerId);
        customerAddress.setAddressId(address.getId());
        customerAddress.setNickname(nickname);
        customerAddress.setDefault(isDefault);

        return customerAddressRepository.save(customerAddress);
    }
}