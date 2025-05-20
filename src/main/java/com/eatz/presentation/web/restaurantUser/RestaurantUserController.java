package com.eatz.presentation.web.restaurantUser;

import com.eatz.application.restaurantUser.services.RestaurantUserService;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserRequest;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserResponse;
import com.eatz.presentation.web.restaurantUser.mapper.RestaurantUserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant-users")
@Tag(name = "Restaurant Users", description = "Endpoints for managing restaurant users")
public class RestaurantUserController {

    private final RestaurantUserService restaurantUserService;
    private final RestaurantUserMapper mapper;

    public RestaurantUserController(
            RestaurantUserService restaurantUserService,
            RestaurantUserMapper mapper
    ) {
        this.restaurantUserService = restaurantUserService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantUserResponse> findById(@PathVariable Long id) {
        RestaurantUser user = restaurantUserService.findById(id);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @PostMapping("/register")
    public ResponseEntity<RestaurantUserResponse> create(@RequestBody @Valid RestaurantUserRequest request) {
        RestaurantUser user = mapper.toDomain(request);
        RestaurantUser created = restaurantUserService.createUser(user);
        return ResponseEntity.ok(mapper.toResponse(created));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse authResponse = restaurantUserService.authenticate(request);
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantUserResponse> update(@PathVariable Long id, @RequestBody @Valid RestaurantUserRequest request) {
        RestaurantUser user = mapper.toDomain(request);
        RestaurantUser updated = restaurantUserService.updateUser(id, user);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid PasswordUpdateRequest request
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        restaurantUserService.updatePassword(token, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}