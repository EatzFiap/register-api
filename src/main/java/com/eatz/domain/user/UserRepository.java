package com.eatz.domain.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByLogin(String login);
    User save(User user);
    void delete(User user);
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<User> findByIdAndAtivoTrue(UUID id);
    Optional<User> findByEmailAndAtivoTrue(String email);
    List<User> findAll();
}