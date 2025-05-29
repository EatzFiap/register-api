package com.eatz.shared.auth;

import com.eatz.shared.dto.AuthenticationResponse;
import com.eatz.shared.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public abstract class AuthenticateUserUseCase {

    protected final AuthenticationManager authenticationManager;

    protected AuthenticateUserUseCase(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public abstract AuthenticationResponse execute(LoginRequest loginRequest);

    protected void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

}
