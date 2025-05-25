package com.eatz.application.address.usecases;

import com.eatz.domain.address.AddressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressUseCaseConfig {

    @Bean
    public SaveAddressUseCase saveAddressUseCase(AddressRepository addressRepository) {
        return new SaveAddressUseCase(addressRepository);
    }

    @Bean
    public DeleteAddressUseCase deleteAddressUseCase(AddressRepository addressRepository) {
        return new DeleteAddressUseCase(addressRepository);
    }

}
