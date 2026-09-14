package com.andef.daily.planner.backend.network.dtos;

/**
 * Данные ошибки
 *
 * @param status Код состояния
 * @param message Сообщение
 */
public record ErrorResponseDto(int status, String message) {
}
