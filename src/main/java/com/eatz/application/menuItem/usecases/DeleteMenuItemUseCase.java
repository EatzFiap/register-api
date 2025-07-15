package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import com.eatz.domain.menuItem.exceptions.MenuItemNotFoundException;

import java.time.LocalDateTime;

public class DeleteMenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    public DeleteMenuItemUseCase(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void execute(Long id) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Item do cardápio não encontrado para exclusão."));

        menuItem.setDeleted(true);
        menuItem.setUpdatedAt(LocalDateTime.now());
        menuItemRepository.save(menuItem);
    }
}