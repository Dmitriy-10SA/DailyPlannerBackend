package com.andef.daily.planner.backend.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Настройки JWT-токенов
 */
@Component
@Getter
public class JwtProperties {

    /**
     * Название поля идентификатора пользователя
     */
    private final String userIdClaim = "userIdClaim";

    /**
     * Срок действия токена
     */
    @Value(value = "${security.jwt.expiration-millis}")
    private long jwtExpirationMillis;

    /**
     * Секретный ключ токена
     */
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    /**
     * Издатель токена
     */
    @Value("${spring.application.name}")
    private String issuer;
}
