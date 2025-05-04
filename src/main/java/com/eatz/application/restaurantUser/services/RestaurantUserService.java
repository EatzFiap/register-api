package com.eatz.application.restaurantUser.services;

import com.eatz.application.restaurantUser.usecases.AuthenticateRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.CreateRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.UpdateRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.DeleteRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.GetRestaurantUserUseCase;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.presentation.web.restaurantUser.dto.AuthenticationResponse;
import com.eatz.presentation.web.restaurantUser.dto.LoginRequest;
import org.springframework.stereotype.Service;



@Service
public class RestaurantUserService {

    private final CreateRestaurantUserUseCase createUserUseCase;
    private final UpdateRestaurantUserUseCase updateUserUseCase;
    private final DeleteRestaurantUserUseCase deleteUserUseCase;
    private final GetRestaurantUserUseCase getUserUseCase;
    private final AuthenticateRestaurantUserUseCase authenticateRestaurantUserUseCase;

    public RestaurantUserService(
            CreateRestaurantUserUseCase createUserUseCase,
            UpdateRestaurantUserUseCase updateUserUseCase,
            DeleteRestaurantUserUseCase deleteUserUseCase,
            GetRestaurantUserUseCase getUserUseCase,
            AuthenticateRestaurantUserUseCase authenticateRestaurantUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.authenticateRestaurantUserUseCase = authenticateRestaurantUserUseCase;
    }

    public RestaurantUser createUser(RestaurantUser restaurantUser) {
        return createUserUseCase.execute(restaurantUser);
    }

    public RestaurantUser findById(Long id) {
        return getUserUseCase.execute(id);
    }

    public RestaurantUser updateUser(Long id, RestaurantUser newData) {
        return updateUserUseCase.execute(id, newData);
    }

    public void deleteUser(Long id) {
        deleteUserUseCase.execute(id);
    }

    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        return authenticateRestaurantUserUseCase.execute(loginRequest);
    }
}