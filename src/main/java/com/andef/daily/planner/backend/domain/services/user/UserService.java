package com.andef.daily.planner.backend.domain.services.user;

import com.andef.daily.planner.backend.data.entities.User;

/**
 * Сервис для {@link User}
 */
public interface UserService {

    /**
     * Находит пользователя по идентификатору
     *
     * @param id Идентификатор пользователя
     * @return Найденный пользователь
     */
    User getById(Long id);

    /**
     * Находит пользователя по логину
     *
     * @param login Логин пользователя
     * @return Найденный пользователь
     */
    User getByLogin(String login);

    /**
     * Проверяет наличие пользователя с логином
     *
     * @param login Логин пользователя
     * @return Признак существования пользователя
     */
    boolean existsByLogin(String login);

    /**
     * Сохраняет пользователя
     *
     * @param user Пользователь
     * @return Сохранённый пользователь
     */
    User save(User user);
}
