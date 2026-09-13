package com.andef.daily.planner.backend.domain.services.user;

import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.data.repositories.UserRepository;
import com.andef.daily.planner.backend.domain.exceptions.LoginAlreadyExistsException;
import com.andef.daily.planner.backend.domain.exceptions.UserNotFoundException;
import com.andef.daily.planner.backend.domain.mappers.UserMapper;
import com.andef.daily.planner.backend.network.dtos.user.RegisterRequestDto;
import com.andef.daily.planner.backend.network.dtos.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса {@link UserService}
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByLogin(request.login())) {
            throw new LoginAlreadyExistsException(request.login());
        }

        String passwordHash = passwordEncoder.encode(request.password());

        User user = userRepository.save(userMapper.toEntity(request, passwordHash));

        return userMapper.toResponse(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Transactional
    @Override
    public UserResponseDto changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));

        return userMapper.toResponse(userRepository.save(user));
    }
}
