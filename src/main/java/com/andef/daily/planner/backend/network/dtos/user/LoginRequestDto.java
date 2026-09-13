package com.andef.daily.planner.backend.network.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Данные входа пользователя
 *
 * @param login    Логин
 * @param password Пароль
 */
public record LoginRequestDto(
        @NotBlank @Size(max = 100) String login,
        @NotBlank String password
) {
}
