package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import com.eatz.domain.menuItem.exceptions.MenuItemNotFoundException;

import java.util.List;

public class GetMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public GetMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem execute(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ID não pode ser nulo.");

        return menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Item do cardápio não encontrado com id: " + id));
    }

    public List<MenuItem> executeByRestaurant(Long restaurantId) {
        if (restaurantId == null)
            throw new IllegalArgumentException("ID do restaurante não pode ser nulo.");

        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }
}