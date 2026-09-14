package com.andef.daily.planner.backend.domain.exceptions;

/**
 * Ошибка неверного логина или пароля
 */
public class InvalidLoginOrPasswordException extends RuntimeException {

    public InvalidLoginOrPasswordException() {
        super("Неверный логин или пароль!");
    }
}
