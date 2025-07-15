package com.eatz.infrastructure.persistence.menuItem;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaMenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findAllByRestaurantIdAndIsDeletedFalse(Long restaurantId);
}