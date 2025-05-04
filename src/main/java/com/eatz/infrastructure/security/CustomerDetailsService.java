package com.eatz.infrastructure.security;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static com.eatz.domain.customer.enums.UserRole.CUSTOMER;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws JwtException {
        Customer customer = customerRepository.findByEmailAndIsDeletedTrue(username)
                .orElseThrow(() -> new JwtException("User not found: " + username));

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
}
