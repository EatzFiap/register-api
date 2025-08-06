package com.eatz.presentation.web.menuItem.mapper;

import com.eatz.application.utils.ValuesConverter;
import com.eatz.domain.menuItem.MenuItem;
import com.eatz.presentation.web.menuItem.dto.MenuItemRequest;
import com.eatz.presentation.web.menuItem.dto.MenuItemResponse;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItem toDomain(MenuItemRequest request) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.name());
        menuItem.setDescription(request.description());
        menuItem.setPrice(ValuesConverter.toBigDecimal(request.price()));
        menuItem.setOnlyLocalConsumption(request.onlyLocalConsumption());
        menuItem.setRestaurantId(request.restaurantId());
        return menuItem;
    }

    public MenuItemResponse toResponse(MenuItem menuItem) {
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                ValuesConverter.toDouble(menuItem.getPrice()),
                menuItem.isOnlyLocalConsumption(),
                menuItem.getPhotoUrl(),
                menuItem.getRestaurantId(),
                menuItem.getCreatedAt(),
                menuItem.getUpdatedAt()
        );
    }
}