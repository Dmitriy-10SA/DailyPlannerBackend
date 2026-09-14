package com.andef.daily.planner.backend.network.controllers;

import com.andef.daily.planner.backend.domain.exceptions.EventNotFoundException;
import com.andef.daily.planner.backend.domain.exceptions.InvalidLoginOrPasswordException;
import com.andef.daily.planner.backend.domain.exceptions.LoginAlreadyExistsException;
import com.andef.daily.planner.backend.domain.exceptions.UserNotFoundException;
import com.andef.daily.planner.backend.network.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

/**
 * Обработчик ошибок API
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Обрабатывает отсутствие сущности
     *
     * @param exception Ошибка отсутствующей сущности
     * @return Данные ошибки
     */
    @ExceptionHandler({UserNotFoundException.class, EventNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(RuntimeException exception) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    /**
     * Обрабатывает занятый логин
     *
     * @param exception Ошибка занятого логина
     * @return Данные ошибки
     */
    @ExceptionHandler(LoginAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleConflict(LoginAlreadyExistsException exception) {
        return response(HttpStatus.CONFLICT, exception.getMessage());
    }

    /**
     * Обрабатывает ошибку аутентификации
     *
     * @param exception Ошибка аутентификации
     * @return Данные ошибки
     */
    @ExceptionHandler({
            InvalidLoginOrPasswordException.class,
            AuthenticationCredentialsNotFoundException.class
    })
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(RuntimeException exception) {
        return response(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    /**
     * Обрабатывает нарушение бизнес-правил
     *
     * @param exception Ошибка бизнес-правила
     * @return Данные ошибки
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequest(IllegalArgumentException exception) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Обрабатывает ошибку валидации
     *
     * @param exception Ошибка валидации
     * @return Данные ошибки
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return "%s: %s".formatted(
                                fieldError.getField(),
                                Objects.requireNonNullElse(
                                        fieldError.getDefaultMessage(),
                                        "Некорректное значение"
                                )
                        );
                    }

                    return Objects.requireNonNullElse(error.getDefaultMessage(), "Некорректные данные");
                })
                .orElse("Некорректные данные");

        return response(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Обрабатывает некорректное тело запроса
     *
     * @param exception Ошибка чтения запроса
     * @return Данные ошибки
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        return response(HttpStatus.BAD_REQUEST, "Некорректное тело запроса");
    }

    /**
     * Обрабатывает некорректный параметр запроса
     *
     * @param exception Ошибка параметра запроса
     * @return Данные ошибки
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return response(
                HttpStatus.BAD_REQUEST,
                "Параметр '%s' содержит некорректное значение".formatted(exception.getName())
        );
    }

    /**
     * Создаёт ответ с ошибкой
     *
     * @param status  Код состояния
     * @param message Сообщение
     * @return Данные ошибки
     */
    private ResponseEntity<ErrorResponseDto> response(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponseDto(status.value(), message));
    }
}
