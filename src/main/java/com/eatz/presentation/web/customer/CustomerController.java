package com.eatz.presentation.web.customer;

import com.eatz.application.customer.services.CustomerService;
import com.eatz.application.customer.usecases.AuthenticateCustomerUseCase;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import com.eatz.presentation.web.customer.dto.AuthenticationResponse;
import com.eatz.presentation.web.customer.dto.LoginRequest;
import com.eatz.presentation.web.customer.dto.CustomerRequest;
import com.eatz.presentation.web.customer.dto.CustomerResponse;
import com.eatz.presentation.web.customer.mapper.CustomerMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Customer customer = customerService.getCustomer(id);
            return ResponseEntity.ok(mapper.toResponse(customer));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Customer customerRequest = mapper.toDomain(request);
            Customer created = customerService.createCustomer(customerRequest);
            return ResponseEntity.ok(mapper.toResponse(created));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.ok(
                    authenticateCustomerUseCase.execute(request)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID id, @RequestBody @Valid CustomerRequest request) {
        if (id == null || request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Customer customerRequest = mapper.toDomain(request);
            Customer updated = customerService.updateCustomer(id, customerRequest);
            return ResponseEntity.ok(mapper.toResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}