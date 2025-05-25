package com.eatz.presentation.web.customer;

import com.eatz.application.customer.services.CustomerService;
import com.eatz.application.customer.usecases.AuthenticateCustomerUseCase;
import com.eatz.domain.customer.Customer;
import com.eatz.presentation.web.customer.dto.*;
import com.eatz.presentation.web.customer.mapper.CustomerMapper;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "Endpoints for managing customers")
public class CustomerController {

    private final CustomerService customerService;
    private final AuthenticateCustomerUseCase authenticateCustomerUseCase;
    private final CustomerMapper mapper;

    public CustomerController(
            CustomerService customerService,
            AuthenticateCustomerUseCase authenticateCustomerUseCase,
            CustomerMapper mapper
    ) {
        this.customerService = customerService;
        this.authenticateCustomerUseCase = authenticateCustomerUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> findByUsername(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        Customer customer = customerService.getCustomerByUsername(token);
        CustomerResponse response = mapper.toResponse(customer);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);
        CustomerResponse response = mapper.toResponse(customer);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest request) {
        Customer customerRequest = mapper.toDomain(request);
        Customer created = customerService.createCustomer(customerRequest);
        CustomerResponse response = mapper.toResponse(created);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse authResponse = authenticateCustomerUseCase.execute(request);
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateCustomerRequest request) {
        Customer customerRequest = mapper.toDomain(request);
        Customer updated = customerService.updateCustomer(id, customerRequest);
        CustomerResponse response = mapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid PasswordUpdateRequest request
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        customerService.updatePassword(token, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/address/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        customerService.deleteAddress(customerId, addressId);
        return ResponseEntity.noContent().build();
    }

}