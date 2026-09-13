package com.andef.daily.planner.backend.network.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Данные регистрации пользователя
 *
 * @param login    Логин
 * @param password Пароль
 */
public record RegisterRequestDto(
        @NotBlank @Size(max = 100) String login,
        @NotBlank String password
) {
}
