package com.andef.daily.planner.backend.domain.exceptions;

/**
 * Ошибка занятого логина
 */
public class LoginAlreadyExistsException extends RuntimeException {

    public LoginAlreadyExistsException(String login) {
        super("Пользователь с логином '%s' уже существует".formatted(login));
    }
}
