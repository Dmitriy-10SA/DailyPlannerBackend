package com.andef.daily.planner.backend.domain.exceptions;

import lombok.NonNull;

/**
 * Ошибка отсутствующего пользователя
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(@NonNull String login) {
        super("Пользователь с логином '%s' не найден".formatted(login));
    }

    public UserNotFoundException(@NonNull Long id) {
        super("Пользователь с идентификатором '%d' не найден".formatted(id));
    }
}
