package com.andef.daily.planner.backend.domain.services.jwt;

import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.domain.services.user.UserService;
import com.andef.daily.planner.backend.security.jwt.JwtFilter;
import com.andef.daily.planner.backend.security.jwt.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * Реализация сервиса {@link JwtService}
 */
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;
    private final UserService userService;

    @Override
    public String createUserToken(User user) {
        return Jwts.builder()
                .claim(jwtProperties.getUserIdClaim(), user.getId())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(getExpirationDate())
                .signWith(getHmacShaKey())
                .compact();
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getHmacShaKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof JwtFilter.UserPrincipal(long id)) {
            return userService.getById(id);
        }

        throw new AuthenticationCredentialsNotFoundException("Аутентификация не содержит данные пользователя");
    }

    /**
     * Возвращает дату окончания токена
     *
     * @return Дата окончания токена
     */
    private Date getExpirationDate() {
        return Date.from(Instant.now().plusMillis(jwtProperties.getJwtExpirationMillis()));
    }

    /**
     * Создаёт ключ подписи токена
     *
     * @return Ключ подписи
     */
    private SecretKey getHmacShaKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getJwtSecret()));
    }
}
