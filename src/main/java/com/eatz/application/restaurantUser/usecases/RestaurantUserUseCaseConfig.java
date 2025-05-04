package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RestaurantUserUseCaseConfig {

    @Bean
    public CreateRestaurantUserUseCase createRestaurantUseCase(RestaurantUserRepository restaurantUserRepository, PasswordEncoder passwordEncoder) {
        return new CreateRestaurantUserUseCase(restaurantUserRepository, passwordEncoder);
    }

    @Bean
    public DeleteRestaurantUserUseCase deleteRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new DeleteRestaurantUserUseCase(restaurantUserRepository);
    }

    @Bean
    public UpdateRestaurantUserUseCase updateRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new UpdateRestaurantUserUseCase(restaurantUserRepository);
    }

    @Bean
    public GetRestaurantUserUseCase getRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new GetRestaurantUserUseCase(restaurantUserRepository);
    }
}
