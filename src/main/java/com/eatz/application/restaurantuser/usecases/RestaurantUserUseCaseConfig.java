package com.eatz.application.restaurantuser.usecases;

import com.eatz.domain.restaurantuser.RestaurantUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RestaurantUserUseCaseConfig {

    @Bean
    public CreateRestaurantUser createRestaurantUseCase(RestaurantUserRepository restaurantUserRepository, PasswordEncoder passwordEncoder) {
        return new CreateRestaurantUser(restaurantUserRepository, passwordEncoder);
    }

    @Bean
    public DeleteRestaurantUser deleteRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new DeleteRestaurantUser(restaurantUserRepository);
    }

    @Bean
    public UpdateRestaurantUser updateRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new UpdateRestaurantUser(restaurantUserRepository);
    }

    @Bean
    public GetRestaurantUser getRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new GetRestaurantUser(restaurantUserRepository);
    }
}
