package com.eatz.application.restaurantUser.services;

import com.eatz.application.address.usecases.CreateAddressUseCase;
import com.eatz.application.restaurantUser.usecases.AuthenticateRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.CreateRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.UpdateRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.DeleteRestaurantUserUseCase;
import com.eatz.application.restaurantUser.usecases.GetRestaurantUserUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import com.eatz.shared.usecases.UpdateUserPasswordUseCase;
import org.springframework.stereotype.Service;

@Service
public class RestaurantUserService {

    private final CreateRestaurantUserUseCase createUserUseCase;
    private final UpdateRestaurantUserUseCase updateUserUseCase;
    private final DeleteRestaurantUserUseCase deleteUserUseCase;
    private final GetRestaurantUserUseCase getUserUseCase;
    private final UpdateUserPasswordUseCase<RestaurantUser> updatePasswordUseCase;
    private final AuthenticateRestaurantUserUseCase authenticateRestaurantUserUseCase;
    private final CreateAddressUseCase createAddressUseCase;
    private final JwtUtil jwtUtil;

    public RestaurantUserService(
            CreateRestaurantUserUseCase createUserUseCase,
            UpdateRestaurantUserUseCase updateUserUseCase,
            DeleteRestaurantUserUseCase deleteUserUseCase,
            GetRestaurantUserUseCase getUserUseCase,
            UpdateUserPasswordUseCase<RestaurantUser> updatePasswordUseCase,
            AuthenticateRestaurantUserUseCase authenticateRestaurantUserUseCase,
            CreateAddressUseCase createAddressUseCase,
            JwtUtil jwtUtil
    ) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.updatePasswordUseCase = updatePasswordUseCase;
        this.authenticateRestaurantUserUseCase = authenticateRestaurantUserUseCase;
        this.createAddressUseCase = createAddressUseCase;
        this.jwtUtil = jwtUtil;
    }

    public RestaurantUser createUser(RestaurantUser restaurantUser) {
        Address savedAddress = createAddressUseCase.execute(restaurantUser.getAddress());
        restaurantUser.setAddress(savedAddress);
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

    public void updatePassword(String token, PasswordUpdateRequest request) {
        String email = jwtUtil.extractUsername(token);
        RestaurantUser user = getUserUseCase.execute(email);
        updatePasswordUseCase.execute(user, request.getOldPassword(), request.getNewPassword());
    }

}