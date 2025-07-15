package com.eatz.application.menuItem.services;

import com.eatz.application.menuItem.usecases.CreateMenuItemUseCase;
import com.eatz.application.menuItem.usecases.DeleteMenuItemUseCase;
import com.eatz.application.menuItem.usecases.GetMenuItemUseCase;
import com.eatz.application.menuItem.usecases.UpdateMenuItemUseCase;
import com.eatz.domain.menuItem.MenuItem;
import com.eatz.infrastructure.storage.MinIOService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final GetMenuItemUseCase getMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;
    private final MinIOService minIOService;

    public MenuItemService(
            CreateMenuItemUseCase createMenuItemUseCase,
            GetMenuItemUseCase getMenuItemUseCase,
            UpdateMenuItemUseCase updateMenuItemUseCase,
            DeleteMenuItemUseCase deleteMenuItemUseCase,
            MinIOService minIOService
    ) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.getMenuItemUseCase = getMenuItemUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
        this.minIOService = minIOService;
    }

    public MenuItem createMenuItem(MenuItem menuItem, String base64Image, String contentType) {
        if (base64Image != null && !base64Image.isBlank()) {
            String photoUrl = minIOService.uploadImageFromBase64(base64Image, contentType);
            menuItem.setPhotoUrl(photoUrl);
        }
        return createMenuItemUseCase.execute(menuItem);
    }

    public MenuItem getMenuItem(Long id) {
        MenuItem menuItem = getMenuItemUseCase.execute(id);
        if (menuItem.getPhotoUrl() != null) {
            String signedUrl = minIOService.generateSignedUrl(menuItem.getPhotoUrl());
            menuItem.setPhotoUrl(signedUrl);
        }
        return menuItem;
    }

    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        List<MenuItem> menuItems = getMenuItemUseCase.executeByRestaurant(restaurantId);
        return menuItems.stream().map(item -> {
            if (item.getPhotoUrl() != null) {
                String signedUrl = minIOService.generateSignedUrl(item.getPhotoUrl());
                item.setPhotoUrl(signedUrl);
            }
            return item;
        }).toList();
    }

    public MenuItem updateMenuItem(Long id, MenuItem newData, String base64Image, String contentType) {
        MenuItem existingItem = getMenuItemUseCase.execute(id);

        if (base64Image != null && !base64Image.isBlank()) {
            // Remove imagem antiga se existir
            if (existingItem.getPhotoUrl() != null) {
                minIOService.removeImage(existingItem.getPhotoUrl());
            }
            // Upload nova imagem
            String photoUrl = minIOService.uploadImageFromBase64(base64Image, contentType);
            newData.setPhotoUrl(photoUrl);
        }

        return updateMenuItemUseCase.execute(id, newData);
    }

    public void deleteMenuItem(Long id) {
        MenuItem menuItem = getMenuItemUseCase.execute(id);

        // Remove imagem se existir
        if (menuItem.getPhotoUrl() != null) {
            minIOService.removeImage(menuItem.getPhotoUrl());
        }

        deleteMenuItemUseCase.execute(id);
    }
}