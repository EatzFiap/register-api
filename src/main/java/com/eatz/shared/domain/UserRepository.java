package com.eatz.shared.domain;

import java.util.Optional;

public interface UserRepository<T extends User> {
    T save(T user);
    boolean existsByEmailAndIsDeletedFalse(String email);
    Optional<T> findByEmailAndIsDeletedFalse(String email);
}
