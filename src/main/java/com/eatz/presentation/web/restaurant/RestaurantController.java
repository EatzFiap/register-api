package com.eatz.presentation.web.restaurant;

import com.eatz.application.restaurant.services.*;
import com.eatz.application.restaurant.usecases.AuthenticateRestaurantUseCase;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.exceptions.RestaurantNotFoundException;
import com.eatz.presentation.web.restaurant.dto.AuthenticationResponse;
import com.eatz.presentation.web.restaurant.dto.LoginRequest;
import com.eatz.presentation.web.restaurant.dto.RestaurantRequest;
import com.eatz.presentation.web.restaurant.dto.RestaurantResponse;
import com.eatz.presentation.web.restaurant.mapper.RestaurantMapper;
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
public class RestaurantController {

    private final GetRestaurantService getRestaurantService;
    private final CreateRestaurantService createRestaurantService;
    private final UpdateRestaurantService updateService;
    private final DeleteRestaurantService deleteService;
    private final AuthenticateRestaurantUseCase authenticateRestaurantUseCase;
    private final RestaurantMapper mapper;

    public RestaurantController(
            GetRestaurantService getRestaurantService,
            CreateRestaurantService createRestaurantService,
            UpdateRestaurantService updateService,
            DeleteRestaurantService deleteService,
            AuthenticateRestaurantUseCase authenticateRestaurantUseCase,
            RestaurantMapper mapper
    ) {
        this.getRestaurantService = getRestaurantService;
        this.createRestaurantService = createRestaurantService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.authenticateRestaurantUseCase = authenticateRestaurantUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Restaurant customer = getRestaurantService.execute(id);
            return ResponseEntity.ok(mapper.toResponse(customer));
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> findAll() {
        try {
            List<Restaurant> customers = getRestaurantService.execute();
            if (customers == null || customers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<RestaurantResponse> responseList = customers.stream()
                    .filter(Objects::nonNull)
                    .map(mapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RestaurantResponse> create(@RequestBody @Valid RestaurantRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Restaurant usuario = mapper.toDomain(request);
            Restaurant created = createRestaurantService.execute(usuario);
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
                    authenticateRestaurantUseCase.execute(request)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> update(@PathVariable UUID id, @RequestBody @Valid RestaurantRequest request) {
        if (id == null || request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Restaurant usuario = mapper.toDomain(request);
            Restaurant updated = updateService.execute(id, usuario);
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