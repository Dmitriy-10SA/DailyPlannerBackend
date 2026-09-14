package com.andef.daily.planner.backend.domain.exceptions;

/**
 * Ошибка отсутствующего пользователя
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String login) {
        super("Пользователь с логином '%s' не найден".formatted(login));
    }

    public UserNotFoundException(Long id) {
        super("Пользователь с идентификатором '%d' не найден".formatted(id));
    }
}
