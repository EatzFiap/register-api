package com.eatz.application.restaurant.usecases;

import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.restaurant.Restaurant;
import com.eatz.domain.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;


@Service
public class UpdateRestaurantUseCase {

    private final RestaurantRepository restaurantRepository;
    private final SaveAddressUseCase saveAddressUseCase;

    public UpdateRestaurantUseCase(RestaurantRepository restaurantRepository, SaveAddressUseCase saveAddressUseCase) {
        this.restaurantRepository = restaurantRepository;
        this.saveAddressUseCase = saveAddressUseCase;
    }

    public Restaurant execute(Restaurant existing, Restaurant newData) {
        if (newData == null || existing == null) {
            throw new IllegalArgumentException("Restaurant data cannot be null");
        }

        existing.setName(newData.getName());
        existing.setLogoImageUrl(newData.getLogoImageUrl());
        existing.setPhone(newData.getPhone());
        existing.setWhatsappPhone(newData.getWhatsappPhone());
        existing.setCnpj(newData.getCnpj());
        existing.setDeliveryRadius(newData.getDeliveryRadius());
        existing.setOwner(existing.getOwner());

        if (newData.getAddress() != null) {
            Address updatedAddress = newData.getAddress();

            if (existing.getAddress() != null) {
                updatedAddress.setId(existing.getAddress().getId());
            }

            Address savedAddress = saveAddressUseCase.execute(updatedAddress);
            existing.setAddress(savedAddress);
        }

        return restaurantRepository.save(existing);
    }
}

