package com.andef.daily.planner.backend.domain.services.user;

import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.network.dtos.user.RegisterRequestDto;
import com.andef.daily.planner.backend.network.dtos.user.UserResponseDto;

/**
 * Сервис для {@link User}
 */
public interface UserService {

    /**
     * Регистрирует пользователя
     *
     * @param request Данные регистрации
     * @return Данные созданного пользователя
     */
    UserResponseDto register(RegisterRequestDto request);

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
     * Изменяет пароль пользователя
     *
     * @param user     Пользователь
     * @param password Новый пароль
     * @return Данные изменённого пользователя
     */
    UserResponseDto changePassword(User user, String password);
}
