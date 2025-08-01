package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import com.eatz.domain.menuItem.exceptions.MenuItemNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteMenuItemUseCaseTest {

    @Mock
    private MenuItemRepository repository;

    private DeleteMenuItemUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        useCase = new DeleteMenuItemUseCase(repository);
    }

    @Test
    void execute_shouldMarkAsDeletedAndSave() {
        MenuItem item = new MenuItem();
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        useCase.execute(1L);

        assertTrue(item.isDeleted());
        assertNotNull(item.getUpdatedAt());
        verify(repository).save(item);
    }

    @Test
    void execute_shouldThrowIfIdNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(null);
        });
        assertEquals("ID não pode ser nulo.", ex.getMessage());
    }

    @Test
    void execute_shouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(1L));
    }
}
