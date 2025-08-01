package com.eatz.infrastructure.persistence.menuItem;

import com.eatz.domain.menuItem.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemRepositoryImplTest {

    private JpaMenuItemRepository jpaRepository;
    private MenuItemRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(JpaMenuItemRepository.class);
        repository = new MenuItemRepositoryImpl(jpaRepository);
    }

    @Test
    void findById_shouldReturnMappedMenuItem() {
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(1L);

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<MenuItem> result = repository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findAllByRestaurantId_shouldReturnMappedList() {
        MenuItemEntity e = new MenuItemEntity();
        e.setId(1L);

        when(jpaRepository.findAllByRestaurantIdAndIsDeletedFalse(1L)).thenReturn(List.of(e));

        List<MenuItem> result = repository.findAllByRestaurantId(1L);
        assertEquals(1, result.size());
    }
}
