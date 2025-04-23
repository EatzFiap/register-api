
package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.customer.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityMapper {

    public Customer toDomain(CustomerEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getPassword(),
                entity.isActive()
        );
    }

    public CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setLogin(customer.getLogin());
        entity.setPassword(customer.getPassword());
        entity.setActive(customer.isActive());
        return entity;
    }
}