package com.eatz.presentation.web.menuItem.mapper;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.presentation.web.menuItem.dto.MenuItemRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemMapperTest {

    private final MenuItemMapper mapper = new MenuItemMapper();

    @Test
    void toDomain_shouldMapFieldsCorrectly() {
        MenuItemRequest request = new MenuItemRequest("Burger", "Delicious", 29.9, false, "base64", "image/png", 1L);
        MenuItem domain = mapper.toDomain(request);

        assertEquals("Burger", domain.getName());
        assertEquals(BigDecimal.valueOf(29.9), domain.getPrice());
    }
}