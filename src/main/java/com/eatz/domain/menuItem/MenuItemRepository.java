package com.eatz.domain.menuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);
    Optional<MenuItem> findById(Long id);
    List<MenuItem> findAllByRestaurantId(Long restaurantId);
    void delete(MenuItem menuItem);
}