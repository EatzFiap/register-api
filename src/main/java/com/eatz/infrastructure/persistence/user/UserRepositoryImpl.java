
package com.eatz.infrastructure.persistence.user;

import com.eatz.domain.user.User;
import com.eatz.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepository;
    private final UserEntityMapper mapper;

    public UserRepositoryImpl(JpaUserRepository jpaRepository, UserEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return jpaRepository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void delete(User user) {
        jpaRepository.delete(mapper.toEntity(user));
    }

    @Override
    public boolean existsByEmailAndIsDeletedFalse(String email) {
        return jpaRepository.existsByEmailAndIsActiveTrue(email);
    }

    @Override
    public Optional<User> findByIdAndAtivoTrue(UUID id) {
        return jpaRepository.findById(id)
                .filter(UserEntity::isActive)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmailAndAtivoTrue(String email) {
        UserEntity userEntity = jpaRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(
                        () -> new IllegalArgumentException("User not found with email: " + email)
                );
        return Optional.of(mapper.toDomain(userEntity));
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
