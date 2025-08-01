package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItem;
import com.eatz.domain.menuItem.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateMenuItemUseCaseTest {

    @Mock
    private MenuItemRepository repository;

    private CreateMenuItemUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        useCase = new CreateMenuItemUseCase(repository);
    }

    @Test
    void execute_shouldSetTimestampsAndCallSave() {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Hamburger");
        menuItem.setPrice(BigDecimal.valueOf(25));

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        MenuItem result = useCase.execute(menuItem);

        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertFalse(result.isDeleted());
        verify(repository).save(menuItem);
    }
}
