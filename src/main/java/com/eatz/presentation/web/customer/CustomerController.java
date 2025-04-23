package com.eatz.presentation.web.customer;

import com.eatz.application.customer.services.CreateCustomerService;
import com.eatz.application.customer.services.DeleteCustomerService;
import com.eatz.application.customer.services.GetCustomerService;
import com.eatz.application.customer.services.UpdateCustomerService;
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
@RequestMapping("/users")
@Tag(name = "User", description = "Endpoints for managing users")
public class CustomerController {

    private final GetCustomerService getCustomerService;
    private final CreateCustomerService createCustomerService;
    private final UpdateCustomerService updateService;
    private final DeleteCustomerService deleteService;
    private final AuthenticateCustomerUseCase authenticateCustomerUseCase;
    private final CustomerMapper mapper;

    public CustomerController(
            GetCustomerService getCustomerService,
            CreateCustomerService createCustomerService,
            UpdateCustomerService updateService,
            DeleteCustomerService deleteService,
            AuthenticateCustomerUseCase authenticateCustomerUseCase,
            CustomerMapper mapper
    ) {
        this.getCustomerService = getCustomerService;
        this.createCustomerService = createCustomerService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.authenticateCustomerUseCase = authenticateCustomerUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Customer customer = getCustomerService.execute(id);
            return ResponseEntity.ok(mapper.toResponse(customer));
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        try {
            List<Customer> customers = getCustomerService.execute();
            if (customers == null || customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<CustomerResponse> responseList = customers.stream()
                    .filter(Objects::nonNull)
                    .map(mapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
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
            Customer usuario = mapper.toDomain(request);
            Customer created = createCustomerService.execute(usuario);
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
            Customer usuario = mapper.toDomain(request);
            Customer updated = updateService.execute(id, usuario);
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
            deleteService.execute(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}