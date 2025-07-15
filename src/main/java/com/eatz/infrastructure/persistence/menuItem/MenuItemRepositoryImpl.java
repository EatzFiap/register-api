package com.eatz.infrastructure.persistence.menuItem;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final JpaMenuItemRepository jpaRepository;

    public MenuItemRepositoryImpl(JpaMenuItemRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity entity = MenuItemEntityMapper.toEntity(menuItem);
        MenuItemEntity saved = jpaRepository.save(entity);
        return MenuItemEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return jpaRepository.findById(id)
                .map(MenuItemEntityMapper::toDomain);
    }

    @Override
    public List<MenuItem> findAllByRestaurantId(Long restaurantId) {
        return jpaRepository.findAllByRestaurantIdAndIsDeletedFalse(restaurantId)
                .stream()
                .map(MenuItemEntityMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(MenuItem menuItem) {
        jpaRepository.delete(MenuItemEntityMapper.toEntity(menuItem));
    }
}