package com.andef.daily.planner.backend.network.controllers.auth;

import com.andef.daily.planner.backend.domain.services.auth.AuthService;
import com.andef.daily.planner.backend.network.dtos.auth.AuthResponseDto;
import com.andef.daily.planner.backend.network.dtos.auth.ChangePasswordDto;
import com.andef.daily.planner.backend.network.dtos.auth.LoginDto;
import com.andef.daily.planner.backend.network.dtos.auth.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер аутентификации
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Регистрирует пользователя
     *
     * @param dto Данные регистрации
     * @return Данные токена доступа
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    /**
     * Выполняет вход пользователя
     *
     * @param dto Данные входа
     * @return Данные токена доступа
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    /**
     * Изменяет пароль пользователя
     *
     * @param dto Данные смены пароля
     * @return Данные нового токена доступа
     */
    @PatchMapping("/change-password")
    public ResponseEntity<AuthResponseDto> changePassword(@Valid @RequestBody ChangePasswordDto dto) {
        return ResponseEntity.ok(authService.changePassword(dto));
    }
}
