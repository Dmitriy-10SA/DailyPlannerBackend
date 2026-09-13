package com.andef.daily.planner.backend.data.repositories;

import com.andef.daily.planner.backend.data.entities.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для {@link User}
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по логину
     *
     * @param login Логин пользователя
     * @return Найденный пользователь
     */
    Optional<@NonNull User> findByLogin(@NonNull String login);

    /**
     * Проверяет наличие пользователя с указанным логином
     *
     * @param login Логин пользователя
     * @return Признак существования пользователя
     */
    boolean existsByLogin(@NonNull String login);
}
