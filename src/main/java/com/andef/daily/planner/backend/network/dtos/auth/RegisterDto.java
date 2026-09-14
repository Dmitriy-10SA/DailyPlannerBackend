package com.andef.daily.planner.backend.network.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Данные регистрации пользователя
 *
 * @param login    Логин
 * @param password Пароль
 */
public record RegisterDto(
        @NotBlank @Size(max = 100) String login,
        @NotBlank String password
) {
}
