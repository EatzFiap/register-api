
package com.eatz.infrastructure.persistence.user;

import com.eatz.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getLogin(),
                entity.getPassword(),
                entity.isActive()
        );
    }

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        entity.setActive(user.isActive());
        return entity;
    }
}