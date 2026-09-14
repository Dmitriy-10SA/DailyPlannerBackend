package com.andef.daily.planner.backend.security.jwt;

import com.andef.daily.planner.backend.domain.services.jwt.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Фильтр JWT-токенов
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    /**
     * Проверяет JWT-токен запроса
     *
     * @param request     HTTP-запрос
     * @param response    HTTP-ответ
     * @param filterChain Цепочка фильтров
     * @throws ServletException При ошибке обработки запроса
     * @throws IOException      При ошибке ввода или вывода
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (Objects.nonNull(authHeader) && authHeader.startsWith(BEARER)) {
            String token = authHeader.substring(7);

            try {
                Claims claims = jwtService.extractClaims(token);

                UsernamePasswordAuthenticationToken authToken = null;

                if (claims.containsKey(jwtProperties.getUserIdClaim())) {
                    long userId = claims.get(jwtProperties.getUserIdClaim(), Long.class);

                    authToken = new UsernamePasswordAuthenticationToken(
                            new UserPrincipal(userId),
                            null,
                            List.of()
                    );
                }

                if (Objects.nonNull(authToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Данные пользователя из JWT-токена
     *
     * @param id Идентификатор пользователя
     */
    public record UserPrincipal(long id) {
    }
}
