package com.eatz.application.address.usecases;

import com.eatz.domain.address.AddressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressUseCaseConfig {

    @Bean
    public CreateAddressUseCase createAddressUseCase(AddressRepository addressRepository) {
        return new CreateAddressUseCase(addressRepository);
    }
}
