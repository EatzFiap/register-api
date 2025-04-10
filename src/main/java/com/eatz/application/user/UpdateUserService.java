package com.eatz.application.user;

import com.eatz.domain.user.User;
import com.eatz.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserService {
    private final UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id, User newData) {
        User user = userRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setName(newData.getName());
        user.setEmail(newData.getEmail());
        user.setPassword(newData.getPassword());
        user.setAddress(newData.getAddress());

        return userRepository.save(user);
    }
}
