package com.eatz.infrastructure.persistence.menuItem;

import com.eatz.domain.menuItem.MenuItem;

public class MenuItemEntityMapper {

    private MenuItemEntityMapper() {}

    public static MenuItem toDomain(MenuItemEntity entity) {
        if (entity == null) return null;

        MenuItem menuItem = new MenuItem();
        menuItem.setId(entity.getId());
        menuItem.setName(entity.getName());
        menuItem.setDescription(entity.getDescription());
        menuItem.setPrice(entity.getPrice());
        menuItem.setOnlyLocalConsumption(entity.isOnlyLocalConsumption());
        menuItem.setPhotoUrl(entity.getPhotoUrl());
        menuItem.setRestaurantId(entity.getRestaurantId());
        menuItem.setCreatedAt(entity.getCreatedAt());
        menuItem.setUpdatedAt(entity.getUpdatedAt());
        menuItem.setDeleted(entity.isDeleted());
        return menuItem;
    }

    public static MenuItemEntity toEntity(MenuItem menuItem) {
        if (menuItem == null) return null;

        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(menuItem.getId());
        entity.setName(menuItem.getName());
        entity.setDescription(menuItem.getDescription());
        entity.setPrice(menuItem.getPrice());
        entity.setOnlyLocalConsumption(menuItem.isOnlyLocalConsumption());
        entity.setPhotoUrl(menuItem.getPhotoUrl());
        entity.setRestaurantId(menuItem.getRestaurantId());
        entity.setCreatedAt(menuItem.getCreatedAt());
        entity.setUpdatedAt(menuItem.getUpdatedAt());
        entity.setDeleted(menuItem.isDeleted());
        return entity;
    }
}