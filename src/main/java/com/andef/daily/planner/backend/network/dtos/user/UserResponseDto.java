package com.andef.daily.planner.backend.network.dtos.user;

/**
 * Данные пользователя
 *
 * @param id    Идентификатор
 * @param login Логин
 */
public record UserResponseDto(Long id, String login) {
}
