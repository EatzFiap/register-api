package com.eatz.application.user;

import com.eatz.domain.user.User;
import com.eatz.domain.user.UserRepository;
import com.eatz.domain.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public List<User> execute() throws UserNotFoundException {
        if (userRepository.findAll().isEmpty()) {
            throw new UserNotFoundException("No users found");
        }
        return userRepository.findAll();
    }
}
