package com.andef.daily.planner.backend.network.dtos;

/**
 * Данные пользователя
 *
 * @param id    Идентификатор
 * @param login Логин
 */
public record UserDto(Long id, String login) {
}
