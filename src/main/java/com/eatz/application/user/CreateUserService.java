package com.eatz.application.user;

import com.eatz.domain.user.User;
import com.eatz.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private final UserRepository userRepository;

    public CreateUserService(UserRepository repository) {
        this.userRepository = repository;
    }

    public User execute(User user) {
        return userRepository.save(user);
    }
}
