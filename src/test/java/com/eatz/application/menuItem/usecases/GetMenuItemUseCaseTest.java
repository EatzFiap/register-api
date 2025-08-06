package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import com.eatz.domain.menuItem.exceptions.MenuItemNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetMenuItemUseCaseTest {

    @Mock
    private MenuItemRepository repository;

    private GetMenuItemUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetMenuItemUseCase(repository);
    }

    @Test
    void execute_shouldReturnItemIfFound() {
        MenuItem item = new MenuItem();
        when(repository.findById(1L)).thenReturn(Optional.of(item));

        MenuItem result = useCase.execute(1L);

        assertNotNull(result);
        verify(repository).findById(1L);
    }

    @Test
    void execute_shouldThrowIfIdNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(null));
    }

    @Test
    void execute_shouldThrowIfNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(1L));
    }

    @Test
    void executeByRestaurant_shouldReturnList() {
        when(repository.findAllByRestaurantId(10L)).thenReturn(List.of(new MenuItem(), new MenuItem()));

        List<MenuItem> results = useCase.executeByRestaurant(10L);

        assertEquals(2, results.size());
        verify(repository).findAllByRestaurantId(10L);
    }

    @Test
    void executeByRestaurant_shouldThrowIfNull() {
        assertThrows(IllegalArgumentException.class, () -> useCase.executeByRestaurant(null));
    }
}
