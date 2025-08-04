package com.eatz.application.restaurantUser.usecases;

import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import com.eatz.domain.restaurantUserType.RestaurantUserTypeRepository;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.usecases.UpdateUserPasswordUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RestaurantUserUseCaseConfig {

    @Bean
    public CreateRestaurantUserUseCase createRestaurantUseCase(RestaurantUserRepository restaurantUserRepository, 
                                                             RestaurantUserTypeRepository restaurantUserTypeRepository,
                                                             PasswordEncoder passwordEncoder) {
        return new CreateRestaurantUserUseCase(restaurantUserRepository, restaurantUserTypeRepository, passwordEncoder);
    }

    @Bean
    public DeleteRestaurantUserUseCase deleteRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new DeleteRestaurantUserUseCase(restaurantUserRepository);
    }

    @Bean
    public UpdateRestaurantUserUseCase updateRestaurantUseCase(RestaurantUserRepository restaurantUserRepository,
                                                             RestaurantUserTypeRepository restaurantUserTypeRepository) {
        return new UpdateRestaurantUserUseCase(restaurantUserRepository, restaurantUserTypeRepository);
    }

    @Bean
    public GetRestaurantUserUseCase getRestaurantUseCase(RestaurantUserRepository restaurantUserRepository) {
        return new GetRestaurantUserUseCase(restaurantUserRepository);
    }

    @Bean
    public UpdateUserPasswordUseCase<RestaurantUser> updateRestaurantUserPasswordUseCase(RestaurantUserRepository restaurantUserRepository, PasswordEncoder passwordEncoder) {
        return new UpdateUserPasswordUseCase<>(restaurantUserRepository, passwordEncoder);
    }

    @Bean
    public AuthenticateRestaurantUserUseCase authenticateRestaurantUserUseCase(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            RestaurantUserRepository restaurantUserRepository
    ) {
        return new AuthenticateRestaurantUserUseCase(
                authenticationManager,
                jwtUtil,
                restaurantUserRepository
        );
    }
}
