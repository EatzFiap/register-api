package com.eatz.infrastructure.security;

import com.eatz.domain.user.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final UserRepository customerRepository;

    public CustomerDetailsService(UserRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws JwtException {
        com.eatz.domain.user.User customer = customerRepository.findByEmailAndAtivoTrue(username)
                .orElseThrow(() -> new JwtException("Usuário não encontrado: " + username));

        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .authorities("CUSTOMER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
