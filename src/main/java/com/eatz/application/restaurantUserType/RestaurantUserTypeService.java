package com.eatz.application.restaurantUserType;

import com.eatz.application.restaurantUserType.usecases.*;
import com.eatz.domain.restaurantUserType.RestaurantUserType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantUserTypeService {
    private final CreateRestaurantUserTypeUseCase createUseCase;
    private final GetRestaurantUserTypeUseCase getUseCase;
    private final UpdateRestaurantUserTypeUseCase updateUseCase;
    private final DeleteRestaurantUserTypeUseCase deleteUseCase;

    public RestaurantUserTypeService(CreateRestaurantUserTypeUseCase createUseCase,
                                   GetRestaurantUserTypeUseCase getUseCase,
                                   UpdateRestaurantUserTypeUseCase updateUseCase,
                                   DeleteRestaurantUserTypeUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.getUseCase = getUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    public RestaurantUserType create(RestaurantUserType restaurantUserType) {
        return createUseCase.execute(restaurantUserType);
    }

    public RestaurantUserType getById(Long id) {
        return getUseCase.executeById(id);
    }

    public List<RestaurantUserType> getAll() {
        return getUseCase.executeFindAll();
    }

    public RestaurantUserType update(Long id, RestaurantUserType restaurantUserType) {
        return updateUseCase.execute(id, restaurantUserType);
    }

    public void delete(Long id) {
        deleteUseCase.execute(id);
    }
} 