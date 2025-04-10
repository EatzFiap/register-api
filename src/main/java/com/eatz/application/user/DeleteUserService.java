package com.eatz.application.user;

import com.eatz.domain.user.User;
import com.eatz.domain.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;

    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id) {
        User user = userRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setActive(false);
        userRepository.save(user);
    }
}
