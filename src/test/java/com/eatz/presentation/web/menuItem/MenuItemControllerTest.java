package com.eatz.presentation.web.menuItem;

import com.eatz.application.menuItem.services.MenuItemService;
import com.eatz.domain.menuItem.MenuItem;
import com.eatz.presentation.web.menuItem.dto.MenuItemRequest;
import com.eatz.presentation.web.menuItem.dto.MenuItemResponse;
import com.eatz.presentation.web.menuItem.mapper.MenuItemMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @Mock
    private MenuItemMapper mapper;

    @InjectMocks
    private MenuItemController controller;

    @Nested
    @DisplayName("Find MenuItem By ID")
    class FindById {

        @Test
        void returnsMenuItemSuccessfully() {
            Long id = 1L;
            MenuItem item = new MenuItem();
            item.setId(id);
            item.setName("Pizza");

            MenuItemResponse response = new MenuItemResponse(id, "Pizza", "desc", 39.9, false, null, 1L, LocalDateTime.now(), LocalDateTime.now());

            when(menuItemService.getMenuItem(id)).thenReturn(item);
            when(mapper.toResponse(item)).thenReturn(response);

            ResponseEntity<MenuItemResponse> result = controller.findById(id);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertNotNull(result.getBody());
            assertEquals("Pizza", result.getBody().getName());
        }

        @Test
        void throwsExceptionWhenNotFound() {
            Long id = 1L;

            when(menuItemService.getMenuItem(id)).thenThrow(new EntityNotFoundException("MenuItem not found"));

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                    () -> controller.findById(id));

            assertEquals("MenuItem not found", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Find By Restaurant ID")
    class FindByRestaurant {

        @Test
        void returnsListOfMenuItemsSuccessfully() {
            Long restaurantId = 10L;
            MenuItem item = new MenuItem();
            item.setId(1L);
            item.setName("Burger");

            MenuItemResponse response = new MenuItemResponse(1L, "Burger", "", 15.0, false, null, 10L, LocalDateTime.now(), LocalDateTime.now());

            when(menuItemService.getMenuItemsByRestaurant(restaurantId)).thenReturn(List.of(item));
            when(mapper.toResponse(item)).thenReturn(response);

            ResponseEntity<List<MenuItemResponse>> result = controller.findByRestaurant(restaurantId);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(1, result.getBody().size());
            assertEquals("Burger", result.getBody().get(0).getName());
        }
    }

    @Nested
    @DisplayName("Create MenuItem")
    class Create {

        @Test
        void createsMenuItemSuccessfully() {
            MenuItemRequest request = new MenuItemRequest("Sushi", "Fresh", 22.5, true, "base64", "image/png", 2L);
            MenuItem domain = new MenuItem();
            domain.setName("Sushi");
            domain.setPrice(BigDecimal.valueOf(22.5));

            MenuItem created = new MenuItem();
            created.setId(1L);
            created.setName("Sushi");
            created.setCreatedAt(LocalDateTime.now());

            MenuItemResponse response = new MenuItemResponse(1L, "Sushi", "Fresh", 22.5, true, null, 2L, created.getCreatedAt(), created.getCreatedAt());

            when(mapper.toDomain(request)).thenReturn(domain);
            when(menuItemService.createMenuItem(domain, "base64", "image/png")).thenReturn(created);
            when(mapper.toResponse(created)).thenReturn(response);

            ResponseEntity<MenuItemResponse> result = controller.create(request);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals("Sushi", result.getBody().getName());
        }
    }

    @Nested
    @DisplayName("Update MenuItem")
    class Update {

        @Test
        void updatesMenuItemSuccessfully() {
            Long id = 1L;
            MenuItemRequest request = new MenuItemRequest("Pizza Updated", "Updated Desc", 42.0, false, "", "", 1L);
            MenuItem newData = new MenuItem();
            newData.setName("Pizza Updated");

            MenuItem updated = new MenuItem();
            updated.setId(1L);
            updated.setName("Pizza Updated");
            updated.setCreatedAt(LocalDateTime.now());
            updated.setUpdatedAt(LocalDateTime.now());

            MenuItemResponse response = new MenuItemResponse(1L, "Pizza Updated", "Updated Desc", 42.0, false, null, 1L, updated.getCreatedAt(), updated.getUpdatedAt());

            when(mapper.toDomain(request)).thenReturn(newData);
            when(menuItemService.updateMenuItem(id, newData, "", "")).thenReturn(updated);
            when(mapper.toResponse(updated)).thenReturn(response);

            ResponseEntity<MenuItemResponse> result = controller.update(id, request);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals("Pizza Updated", result.getBody().getName());
        }
    }

    @Nested
    @DisplayName("Delete MenuItem")
    class Delete {

        @Test
        void deletesSuccessfully() {
            Long id = 1L;

            doNothing().when(menuItemService).deleteMenuItem(id);

            ResponseEntity<Void> result = controller.delete(id);

            assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        }
    }
}
