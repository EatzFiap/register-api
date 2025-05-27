package com.eatz.infrastructure.security;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.RestaurantUserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.eatz.domain.customer.enums.UserRole.ADMIN;
import static com.eatz.domain.customer.enums.UserRole.CUSTOMER;

@Service
public class CompositeUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final RestaurantUserRepository restaurantUserRepository;

    public CompositeUserDetailsService(CustomerRepository customerRepository,
                                       RestaurantUserRepository restaurantUserRepository) {
        this.customerRepository = customerRepository;
        this.restaurantUserRepository = restaurantUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws JwtException {
        Optional<Customer> customerOpt = customerRepository.findByEmailAndIsDeletedFalse(username);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            return User.builder()
                    .username(customer.getEmail())
                    .password(customer.getPassword())
                    .authorities(String.valueOf(CUSTOMER))
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }

        Optional<RestaurantUser> restaurantUserOpt = restaurantUserRepository.findByEmailAndIsDeletedFalse(username);
        if (restaurantUserOpt.isPresent()) {
            RestaurantUser restaurant = restaurantUserOpt.get();
            return User.builder()
                    .username(restaurant.getEmail())
                    .password(restaurant.getPassword())
                    .authorities(String.valueOf(ADMIN))
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
