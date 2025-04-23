package com.eatz.application.restaurant.usecases;

import com.eatz.domain.restaurant.RestaurantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RestaurantUseCaseConfig {

    @Bean
    public CreateRestaurantUseCase createRestaurantUseCase(RestaurantRepository RestaurantRepository, PasswordEncoder passwordEncoder) {
        return new CreateRestaurantUseCase(RestaurantRepository, passwordEncoder);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantUseCase(RestaurantRepository RestaurantRepository) {
        return new DeleteRestaurantUseCase(RestaurantRepository);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurantUseCase(RestaurantRepository RestaurantRepository) {
        return new UpdateRestaurantUseCase(RestaurantRepository);
    }

    @Bean
    public GetRestaurantUseCase getRestaurantUseCase(RestaurantRepository RestaurantRepository) {
        return new GetRestaurantUseCase(RestaurantRepository);
    }
}
