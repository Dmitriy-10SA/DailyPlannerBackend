package com.andef.daily.planner.backend.domain.services.auth;

import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.domain.exceptions.InvalidLoginOrPasswordException;
import com.andef.daily.planner.backend.domain.exceptions.LoginAlreadyExistsException;
import com.andef.daily.planner.backend.domain.exceptions.UserNotFoundException;
import com.andef.daily.planner.backend.domain.mappers.UserMapper;
import com.andef.daily.planner.backend.domain.services.jwt.JwtService;
import com.andef.daily.planner.backend.domain.services.user.UserService;
import com.andef.daily.planner.backend.network.dtos.auth.AuthResponseDto;
import com.andef.daily.planner.backend.network.dtos.auth.ChangePasswordDto;
import com.andef.daily.planner.backend.network.dtos.auth.LoginDto;
import com.andef.daily.planner.backend.network.dtos.auth.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса {@link AuthService}
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    @Transactional
    @Override
    public AuthResponseDto register(RegisterDto request) {
        if (userService.existsByLogin(request.login())) {
            throw new LoginAlreadyExistsException(request.login());
        }

        String passwordHash = passwordEncoder.encode(request.password());

        User user = userService.save(userMapper.toEntity(request, passwordHash));

        return createUserTokenAndReturnAuthResponseDto(user);
    }

    @Transactional
    @Override
    public AuthResponseDto changePassword(ChangePasswordDto dto) {
        User user = userService.getByLogin(dto.login());

        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        return createUserTokenAndReturnAuthResponseDto(user);
    }

    @Transactional(readOnly = true)
    @Override
    public AuthResponseDto login(LoginDto dto) {
        try {
            User user = userService.getByLogin(dto.login());

            if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
                throw new InvalidLoginOrPasswordException();
            }

            return createUserTokenAndReturnAuthResponseDto(user);
        } catch (InvalidLoginOrPasswordException | UserNotFoundException ignore) {
            throw new InvalidLoginOrPasswordException();
        }
    }

    /**
     * Создаёт ответ с токеном пользователя
     *
     * @param user Пользователь
     * @return Данные токена доступа
     */
    private AuthResponseDto createUserTokenAndReturnAuthResponseDto(User user) {
        String userToken = jwtService.createUserToken(user);

        return new AuthResponseDto(userToken);
    }
}
