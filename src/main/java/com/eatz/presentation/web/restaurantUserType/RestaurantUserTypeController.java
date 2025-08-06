package com.eatz.presentation.web.restaurantUserType;

import com.eatz.application.restaurantUserType.RestaurantUserTypeService;
import com.eatz.domain.restaurantUserType.RestaurantUserType;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeRequest;
import com.eatz.presentation.web.restaurantUserType.dto.RestaurantUserTypeResponse;
import com.eatz.presentation.web.restaurantUserType.mapper.RestaurantUserTypeMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurant-user-types")
public class RestaurantUserTypeController {
    private final RestaurantUserTypeService service;

    public RestaurantUserTypeController(RestaurantUserTypeService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantUserTypeResponse>> findAll() {
        List<RestaurantUserType> types = service.getAll();
        List<RestaurantUserTypeResponse> responses = types.stream()
                .map(RestaurantUserTypeMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantUserTypeResponse> findById(@PathVariable Long id) {
        RestaurantUserType type = service.getById(id);
        RestaurantUserTypeResponse response = RestaurantUserTypeMapper.toResponse(type);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RestaurantUserTypeResponse> create(@Valid @RequestBody RestaurantUserTypeRequest request) {
        RestaurantUserType type = RestaurantUserTypeMapper.toDomain(request);
        RestaurantUserType createdType = service.create(type);
        RestaurantUserTypeResponse response = RestaurantUserTypeMapper.toResponse(createdType);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantUserTypeResponse> update(@PathVariable Long id, @Valid @RequestBody RestaurantUserTypeRequest request) {
        RestaurantUserType type = RestaurantUserTypeMapper.toDomain(request);
        RestaurantUserType updatedType = service.update(id, type);
        RestaurantUserTypeResponse response = RestaurantUserTypeMapper.toResponse(updatedType);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
} 