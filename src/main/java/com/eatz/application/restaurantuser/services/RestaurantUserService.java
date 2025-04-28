package com.eatz.application.restaurantuser.services;

import com.eatz.application.restaurantuser.usecases.CreateRestaurantUser;
import com.eatz.application.restaurantuser.usecases.DeleteRestaurantUser;
import com.eatz.application.restaurantuser.usecases.GetRestaurantUser;
import com.eatz.application.restaurantuser.usecases.UpdateRestaurantUser;
import com.eatz.domain.restaurantuser.RestaurantUser;
import com.eatz.domain.restaurantuser.exceptions.RestaurantNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestaurantUserService {

    private final CreateRestaurantUser createUserUseCase;
    private final UpdateRestaurantUser updateUserUseCase;
    private final DeleteRestaurantUser deleteUserUseCase;
    private final GetRestaurantUser getUserUseCase;

    public RestaurantUserService(
            CreateRestaurantUser createUserUseCase,
            UpdateRestaurantUser updateUserUseCase,
            DeleteRestaurantUser deleteUserUseCase,
            GetRestaurantUser getUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    public RestaurantUser createUser(RestaurantUser restaurantUser) {
        return createUserUseCase.execute(restaurantUser);
    }

    public RestaurantUser findById(UUID id) throws RestaurantNotFoundException {
        return getUserUseCase.execute(id);
    }

    public RestaurantUser updateUser(UUID id, RestaurantUser newData) throws RestaurantNotFoundException {
        RestaurantUser existingUser = getUserUseCase.execute(id);
        return updateUserUseCase.execute(existingUser, newData);
    }

    public void deleteUser(UUID id) throws RestaurantNotFoundException {
        RestaurantUser user = getUserUseCase.execute(id);
        deleteUserUseCase.execute(user);
    }

}