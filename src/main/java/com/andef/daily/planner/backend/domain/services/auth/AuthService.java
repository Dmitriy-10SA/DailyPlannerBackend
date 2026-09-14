package com.andef.daily.planner.backend.domain.services.auth;

import com.andef.daily.planner.backend.network.dtos.auth.AuthResponseDto;
import com.andef.daily.planner.backend.network.dtos.auth.ChangePasswordDto;
import com.andef.daily.planner.backend.network.dtos.auth.LoginDto;
import com.andef.daily.planner.backend.network.dtos.auth.RegisterDto;

/**
 * Сервис аутентификации
 */
public interface AuthService {

    /**
     * Регистрирует пользователя
     *
     * @param request Данные регистрации
     * @return Данные токена доступа
     */
    AuthResponseDto register(RegisterDto request);

    /**
     * Изменяет пароль пользователя
     *
     * @param dto Данные смены пароля
     * @return Данные нового токена доступа
     */
    AuthResponseDto changePassword(ChangePasswordDto dto);

    /**
     * Выполняет вход пользователя
     *
     * @param dto Данные входа
     * @return Данные токена доступа
     */
    AuthResponseDto login(LoginDto dto);
}
