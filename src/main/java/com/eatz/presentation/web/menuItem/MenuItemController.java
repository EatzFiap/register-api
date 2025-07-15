package com.eatz.presentation.web.menuItem;

import com.eatz.application.menuItem.services.MenuItemService;
import com.eatz.domain.menuItem.MenuItem;
import com.eatz.presentation.web.menuItem.dto.MenuItemRequest;
import com.eatz.presentation.web.menuItem.dto.MenuItemResponse;
import com.eatz.presentation.web.menuItem.mapper.MenuItemMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@Tag(name = "Menu Items", description = "Endpoints for managing menu items")
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final MenuItemMapper mapper;

    public MenuItemController(MenuItemService menuItemService, MenuItemMapper mapper) {
        this.menuItemService = menuItemService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponse> findById(@PathVariable Long id) {
        MenuItem menuItem = menuItemService.getMenuItem(id);
        MenuItemResponse response = mapper.toResponse(menuItem);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemResponse>> findByRestaurant(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurant(restaurantId);
        List<MenuItemResponse> responses = menuItems.stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> create(@RequestBody @Valid MenuItemRequest request) {
        MenuItem menuItemRequest = mapper.toDomain(request);
        MenuItem created = menuItemService.createMenuItem(
                menuItemRequest,
                request.photoBase64(),
                request.photoContentType()
        );
        MenuItemResponse response = mapper.toResponse(created);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid MenuItemRequest request) {

        MenuItem menuItemRequest = mapper.toDomain(request);
        MenuItem updated = menuItemService.updateMenuItem(
                id,
                menuItemRequest,
                request.photoBase64(),
                request.photoContentType()
        );
        MenuItemResponse response = mapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }
}