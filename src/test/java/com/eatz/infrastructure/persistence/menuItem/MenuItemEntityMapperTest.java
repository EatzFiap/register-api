package com.eatz.infrastructure.persistence.menuItem;

import com.eatz.domain.menuItem.MenuItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemEntityMapperTest {

    @Test
    void toDomain_shouldMapCorrectly() {
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setPrice(BigDecimal.TEN);
        entity.setCreatedAt(LocalDateTime.now());

        MenuItem domain = MenuItemEntityMapper.toDomain(entity);
        assertEquals("Test", domain.getName());
    }

    @Test
    void toEntity_shouldMapCorrectly() {
        MenuItem domain = new MenuItem();
        domain.setId(1L);
        domain.setName("Entity");
        domain.setPrice(BigDecimal.ONE);

        MenuItemEntity entity = MenuItemEntityMapper.toEntity(domain);
        assertEquals("Entity", entity.getName());
    }
}
