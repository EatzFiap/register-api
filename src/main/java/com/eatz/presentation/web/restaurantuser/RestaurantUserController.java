package com.eatz.presentation.web.restaurantuser;

import com.eatz.application.restaurantuser.services.*;
import com.eatz.application.restaurantuser.usecases.AuthenticateRestaurantUser;
import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.domain.restaurantuser.exceptions.RestaurantNotFoundException;
import com.eatz.presentation.web.restaurantuser.dto.AuthenticationResponse;
import com.eatz.presentation.web.restaurantuser.dto.LoginRequest;
import com.eatz.presentation.web.restaurantuser.dto.RestaurantUserRequest;
import com.eatz.presentation.web.restaurantuser.dto.RestaurantUserResponse;
import com.eatz.presentation.web.restaurantuser.mapper.RestaurantUserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurant-users")
@Tag(name = "Restaurant Users", description = "Endpoints for managing restaurant users")
public class RestaurantUserController {

    private final RestaurantUserService restaurantUserService;
    private final AuthenticateRestaurantUser authenticateRestaurantUser;
    private final RestaurantUserMapper mapper;

    public RestaurantUserController(
            RestaurantUserService restaurantUserService,
            AuthenticateRestaurantUser authenticateRestaurantUser,
            RestaurantUserMapper mapper
    ) {
        this.restaurantUserService = restaurantUserService;
        this.authenticateRestaurantUser = authenticateRestaurantUser;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantUserResponse> findById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            RestaurantUser customer = restaurantUserService.findById(id);
            return ResponseEntity.ok(mapper.toResponse(customer));
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RestaurantUserResponse> create(@RequestBody @Valid RestaurantUserRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            RestaurantUser user = mapper.toDomain(request);
            RestaurantUser created = restaurantUserService.createUser(user);
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
                    authenticateRestaurantUser.execute(request)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantUserResponse> update(@PathVariable UUID id, @RequestBody @Valid RestaurantUserRequest request) {
        if (id == null || request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            RestaurantUser user = mapper.toDomain(request);
            RestaurantUser updated = restaurantUserService.updateUser(id, user);
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
            restaurantUserService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}