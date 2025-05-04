package com.eatz.application.customerAddresses.usecases;

import com.eatz.domain.customerAddresses.CustomerAddressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerAddressesUseCaseConfig {

    @Bean
    public AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase(
            CustomerAddressRepository customerAddressRepository) {
        return new AssociateAddressToCustomerUseCase(customerAddressRepository);
    }
}
