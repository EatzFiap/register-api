package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import com.eatz.domain.menuItem.exceptions.MenuItemNotFoundException;

import java.time.LocalDateTime;

public class UpdateMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public UpdateMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem execute(Long id, MenuItem newData) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");
        if (newData == null) throw new IllegalArgumentException("Dados para atualização não podem ser nulos.");

        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Item do cardápio não encontrado para atualização."));

        if (newData.getName() != null) existingMenuItem.setName(newData.getName());
        if (newData.getDescription() != null) existingMenuItem.setDescription(newData.getDescription());
        if (newData.getPrice() != null) existingMenuItem.setPrice(newData.getPrice());
        if (newData.getPhotoUrl() != null) existingMenuItem.setPhotoUrl(newData.getPhotoUrl());
        existingMenuItem.setOnlyLocalConsumption(newData.isOnlyLocalConsumption());
        existingMenuItem.setUpdatedAt(LocalDateTime.now());

        return menuItemRepository.save(existingMenuItem);
    }
}