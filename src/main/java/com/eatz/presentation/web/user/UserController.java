package com.eatz.presentation.web.user;

import com.eatz.application.user.*;
import com.eatz.domain.user.User;
import com.eatz.domain.user.exceptions.UserNotFoundException;
import com.eatz.presentation.web.user.dto.AuthenticationResponse;
import com.eatz.presentation.web.user.dto.LoginRequest;
import com.eatz.presentation.web.user.dto.UserRequest;
import com.eatz.presentation.web.user.dto.UserResponse;
import com.eatz.presentation.web.user.mapper.UserMapper;
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
public class UserController {

    private final GetUserService getUserService;
    private final CreateUserService createUserService;
    private final UpdateUserService updateService;
    private final DeleteUserService deleteService;
    private final AuthenticateCustomerUseCase authenticateCustomerUseCase;
    private final UserMapper mapper;

    public UserController(
            GetUserService getUserService,
            CreateUserService createUserService,
            UpdateUserService updateService,
            DeleteUserService deleteService,
            AuthenticateCustomerUseCase authenticateCustomerUseCase,
            UserMapper mapper
    ) {
        this.getUserService = getUserService;
        this.createUserService = createUserService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.authenticateCustomerUseCase = authenticateCustomerUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            User user = getUserService.execute(id);
            return ResponseEntity.ok(mapper.toResponse(user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        try {
            List<User> users = getUserService.execute();
            if (users == null || users.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            List<UserResponse> responseList = users.stream()
                    .filter(Objects::nonNull)
                    .map(mapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            User usuario = mapper.toDomain(request);
            User created = createUserService.execute(usuario);
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
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody @Valid UserRequest request) {
        if (id == null || request == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            User usuario = mapper.toDomain(request);
            User updated = updateService.execute(id, usuario);
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