package com.andef.daily.planner.backend.network.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Данные смены пароля
 *
 * @param login       Логин
 * @param newPassword Новый пароль
 */
public record ChangePasswordDto(
        @NotBlank @Size(max = 100) String login,
        @NotBlank String newPassword
) {
}
