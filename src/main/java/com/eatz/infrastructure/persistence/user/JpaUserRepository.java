package com.eatz.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByLogin(String login);
    boolean existsByEmailAndIsActiveTrue(String email);
    Optional<UserEntity> findByEmailAndIsActiveTrue(String email);
}
