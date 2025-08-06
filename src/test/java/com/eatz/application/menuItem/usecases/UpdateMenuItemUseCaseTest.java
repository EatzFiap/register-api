package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import com.eatz.domain.menuItem.exceptions.MenuItemNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateMenuItemUseCaseTest {

    @Mock
    private MenuItemRepository repository;

    private UpdateMenuItemUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        useCase = new UpdateMenuItemUseCase(repository);
    }

    @Test
    void execute_shouldUpdateFieldsAndSave() {
        MenuItem existing = new MenuItem();
        existing.setId(1L);
        existing.setName("Old");

        MenuItem update = new MenuItem();
        update.setName("New Name");
        update.setPrice(BigDecimal.valueOf(59.99));
        update.setPhotoUrl("new_url");
        update.setOnlyLocalConsumption(true);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        MenuItem result = useCase.execute(1L, update);

        assertEquals("New Name", result.getName());
        assertEquals("new_url", result.getPhotoUrl());
        assertEquals(BigDecimal.valueOf(59.99), result.getPrice());
        assertTrue(result.isOnlyLocalConsumption());
        assertNotNull(result.getUpdatedAt());

        verify(repository).save(existing);
    }

    @Test
    void execute_shouldThrowIfIdNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null, new MenuItem()));
    }

    @Test
    void execute_shouldThrowIfNewDataNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(1L, null));
    }

    @Test
    void execute_shouldThrowIfItemNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(1L, new MenuItem()));
    }
}
