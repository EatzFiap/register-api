package com.eatz.shared.usecases;

import com.eatz.shared.exception.InvalidPasswordException;
import com.eatz.shared.domain.User;
import com.eatz.shared.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UpdateUserPasswordUseCase<T extends User> {

    private final UserRepository<T> repository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserPasswordUseCase(UserRepository<T> repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(T user, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException();
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        repository.save(user);
    }

}
