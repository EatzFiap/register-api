package com.eatz.application.menuItem.usecases;

import com.eatz.domain.menuItem.MenuItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemUseCaseConfig {

    @Bean
    public CreateMenuItemUseCase createMenuItemUseCase(MenuItemRepository menuItemRepository) {
        return new CreateMenuItemUseCase(menuItemRepository);
    }

    @Bean
    public GetMenuItemUseCase getMenuItemUseCase(MenuItemRepository menuItemRepository) {
        return new GetMenuItemUseCase(menuItemRepository);
    }

    @Bean
    public UpdateMenuItemUseCase updateMenuItemUseCase(MenuItemRepository menuItemRepository) {
        return new UpdateMenuItemUseCase(menuItemRepository);
    }

    @Bean
    public DeleteMenuItemUseCase deleteMenuItemUseCase(MenuItemRepository menuItemRepository) {
        return new DeleteMenuItemUseCase(menuItemRepository);
    }
}