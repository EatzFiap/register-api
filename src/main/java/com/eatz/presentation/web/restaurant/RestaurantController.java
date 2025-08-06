package com.eatz.presentation.web.restaurant;

import com.eatz.application.restaurant.services.RestaurantService;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.presentation.web.restaurant.dto.RestaurantRequest;
import com.eatz.presentation.web.restaurant.dto.RestaurantResponse;
import com.eatz.presentation.web.restaurant.mapper.RestaurantMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody RestaurantRequest request) {
        Restaurant domain = RestaurantMapper.toDomain(request);
        Restaurant created = service.createRestaurant(domain);
        return ResponseEntity.ok(RestaurantMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findById(@PathVariable Long id) {
        Restaurant restaurant = service.getRestaurantById(id);
        return ResponseEntity.ok(RestaurantMapper.toResponse(restaurant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequest request
    ) {
        Restaurant updated = service.updateRestaurant(id, RestaurantMapper.toDomain(request));
        return ResponseEntity.ok(RestaurantMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
