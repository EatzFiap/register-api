package com.eatz.presentation.web.restaurantUser;

import com.eatz.application.restaurantUser.services.RestaurantUserService;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserRequest;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserResponse;
import com.eatz.presentation.web.restaurantUser.dto.UpdateRestaurantUserRequest;
import com.eatz.presentation.web.restaurantUser.mapper.RestaurantUserMapper;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @GetMapping
    public ResponseEntity<RestaurantUserResponse> findByUsername(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.replace("Bearer ", "");
        RestaurantUser user = restaurantUserService.getUserByUsername(token);
        RestaurantUserResponse response = mapper.toResponse(user);
        return ResponseEntity.ok(response);
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
        URI location = URI.create("/customers/" + created.getId());
        return ResponseEntity.created(location).body(mapper.toResponse(created));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse authResponse = restaurantUserService.authenticate(request);
        return ResponseEntity.ok(authResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantUserResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateRestaurantUserRequest request) {
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