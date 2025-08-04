package com.eatz.application.restaurantUserType;

import com.eatz.application.restaurantUserType.usecases.*;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantUserTypeUseCaseConfig {

    @Bean
    public CreateRestaurantUserTypeUseCase createRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository) {
        return new CreateRestaurantUserTypeUseCase(repository);
    }

    @Bean
    public GetRestaurantUserTypeUseCase getRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository) {
        return new GetRestaurantUserTypeUseCase(repository);
    }

    @Bean
    public UpdateRestaurantUserTypeUseCase updateRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository) {
        return new UpdateRestaurantUserTypeUseCase(repository);
    }

    @Bean
    public DeleteRestaurantUserTypeUseCase deleteRestaurantUserTypeUseCase(RestaurantUserTypeRepository repository,
                                                                         RestaurantUserRepository restaurantUserRepository) {
        return new DeleteRestaurantUserTypeUseCase(repository, restaurantUserRepository);
    }
} 