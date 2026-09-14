package com.andef.daily.planner.backend.domain.services.jwt;

import com.andef.daily.planner.backend.data.entities.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

/**
 * Сервис JWT-токенов
 */
public interface JwtService {

    /**
     * Создаёт токен пользователя
     *
     * @param user Пользователь
     * @return JWT-токен
     */
    String createUserToken(User user);

    /**
     * Извлекает данные токена
     *
     * @param token JWT-токен
     * @return Данные токена
     */
    Claims extractClaims(String token);

    /**
     * Возвращает аутентифицированного пользователя
     *
     * @param authentication Данные аутентификации
     * @return Пользователь
     */
    User getUserFromAuthentication(Authentication authentication);
}
