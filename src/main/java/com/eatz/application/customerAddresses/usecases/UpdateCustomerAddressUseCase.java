package com.eatz.application.customerAddresses.usecases;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.AddressRepository;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customerAddresses.CustomerAddress;
import com.eatz.domain.customerAddresses.CustomerAddressRepository;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.shared.exception.AddressNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateCustomerAddressUseCase {

    private final AddressRepository addressRepository;
    private final CustomerAddressRepository customerAddressRepository;

    public UpdateCustomerAddressUseCase(AddressRepository addressRepository,
                                        CustomerAddressRepository customerAddressRepository) {
        this.addressRepository = addressRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

    public void execute(Customer customer, Long addressId, AddressRequest request) {

        List<CustomerAddress> customerAddresses = customerAddressRepository.findByCustomerId(customer.getId());
        if (customerAddresses.isEmpty()) {
            throw new AddressNotFoundException("Nenhum endereço encontrado para o cliente");
        }
        CustomerAddress customerAddress = customerAddresses.stream()
                .filter(addr -> addr.getAddressId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado na lista de endereços do cliente"));

        customerAddress.setNickname(request.getNickname());
        customerAddress.setDefault(request.isDefaultAddress());
        customerAddress.setUpdatedAt(LocalDateTime.now());

        customerAddressRepository.save(customerAddress);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Endereço base não encontrado"));

        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setComplement(request.getComplement());
        address.setCity(request.getCity());
        address.setNeighbourhood(request.getNeighbourhood());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setUpdatedAt(LocalDateTime.now().toString());

        addressRepository.save(address);
    }

}