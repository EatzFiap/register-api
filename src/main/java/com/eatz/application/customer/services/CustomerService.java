package com.eatz.application.customer.services;

import com.eatz.application.address.usecases.DeleteAddressUseCase;
import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.application.customer.usecases.CreateCustomerUseCase;
import com.eatz.application.customer.usecases.DeleteCustomerUseCase;
import com.eatz.application.customer.usecases.GetCustomerUseCase;
import com.eatz.application.customer.usecases.UpdateCustomerUseCase;
import com.eatz.application.customerAddresses.usecases.AssociateAddressToCustomerUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.customer.Customer;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.presentation.web.address.mapper.AddressMapper;
import com.eatz.shared.dto.PasswordUpdateRequest;
import com.eatz.shared.exceptions.AddressNotFoundException;
import com.eatz.shared.usecases.UpdateUserPasswordUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final SaveAddressUseCase saveAddressUseCase;
    private final AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase;
    private final UpdateUserPasswordUseCase<Customer> updatePasswordUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;
    private final JwtUtil jwtUtil;
    private final AddressMapper addressMapper;

    public CustomerService(
            CreateCustomerUseCase createCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            GetCustomerUseCase getCustomerUseCase,
            SaveAddressUseCase saveAddressUseCase,
            AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase,
            UpdateUserPasswordUseCase<Customer> updatePasswordUseCase,
            DeleteAddressUseCase deleteAddressUseCase,
            JwtUtil jwtUtil,
            AddressMapper addressMapper
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.saveAddressUseCase = saveAddressUseCase;
        this.associateAddressToCustomerUseCase = associateAddressToCustomerUseCase;
        this.updatePasswordUseCase = updatePasswordUseCase;
        this.deleteAddressUseCase = deleteAddressUseCase;
        this.jwtUtil = jwtUtil;
        this.addressMapper = addressMapper;
    }

    public Customer createCustomer(Customer customer, AddressRequest addressRequest) {
        Customer createdCustomer = createCustomerUseCase.execute(customer);

        if (customer.getAddresses() != null && addressRequest != null) {
            for (Address address : customer.getAddresses()) {
                Address savedAddress = saveAddressUseCase.execute(address);
                associateAddressToCustomerUseCase.execute(
                        createdCustomer.getId(),
                        savedAddress,
                        addressRequest.getNickname(),
                        addressRequest.isDefault()
                );
            }
        }

        return createdCustomer;
    }

    public Customer getCustomer(Long customerId) {
        return getCustomerUseCase.execute(customerId);
    }

    public Customer updateCustomer(Long customerId, Customer newData) {
        return updateCustomerUseCase.execute(customerId, newData);
    }

    public void deleteCustomer(Long customerId) {
        deleteCustomerUseCase.execute(customerId);
    }

    public void updatePassword(String token, PasswordUpdateRequest request) {

        String email = jwtUtil.extractUsername(token);
        Customer customer = getCustomerUseCase.execute(email);
        updatePasswordUseCase.execute(customer, request.getOldPassword(), request.getNewPassword());

    }

    public Customer getCustomerByUsername(String token) {
        String email = jwtUtil.extractUsername(token);
        return getCustomerUseCase.execute(email);
    }

    public void addAddress(Long id, AddressRequest req) {
        Customer customer = getCustomerUseCase.execute(id);
        Address savedAddress = saveAddressUseCase.execute(addressMapper.toDomain(req));
        associateAddressToCustomerUseCase.execute(customer.getId(), savedAddress, req.getNickname(), req.isDefault());
    }

    public void deleteAddress(Long customerId, Long addressId) {
        Customer customer = getCustomerUseCase.execute(customerId);

        Optional<Address> addressOptional = customer.getAddresses().stream()
                .filter(addr -> addr.getId().equals(addressId))
                .findFirst();

        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            deleteAddressUseCase.execute(address);
        } else {
            throw new AddressNotFoundException();
        }

    }

}