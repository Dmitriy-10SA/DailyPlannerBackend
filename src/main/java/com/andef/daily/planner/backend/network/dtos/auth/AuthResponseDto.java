package com.andef.daily.planner.backend.network.dtos.auth;

/**
 * Данные токена доступа
 *
 * @param jwt JWT-токен
 */
public record AuthResponseDto(String jwt) {
}
