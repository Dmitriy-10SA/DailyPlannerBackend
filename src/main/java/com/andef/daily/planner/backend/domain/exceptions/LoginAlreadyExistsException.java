package com.andef.daily.planner.backend.domain.exceptions;

import lombok.NonNull;

/**
 * Ошибка занятого логина
 */
public class LoginAlreadyExistsException extends RuntimeException {

    public LoginAlreadyExistsException(@NonNull String login) {
        super("Пользователь с логином '%s' уже существует".formatted(login));
    }
}
