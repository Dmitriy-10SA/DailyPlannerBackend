package com.andef.daily.planner.backend.domain.mappers;

import com.andef.daily.planner.backend.data.entities.User;
import com.andef.daily.planner.backend.network.dtos.UserDto;
import com.andef.daily.planner.backend.network.dtos.auth.RegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Маппер пользователей
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Преобразует данные регистрации в пользователя
     *
     * @param request      Данные регистрации
     * @param passwordHash Хеш пароля
     * @return Пользователь
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", source = "request.login")
    @Mapping(target = "password", source = "passwordHash")
    User toEntity(RegisterDto request, String passwordHash);

    /**
     * Преобразует пользователя в данные ответа
     *
     * @param user Пользователь
     * @return Данные пользователя
     */
    UserDto toResponse(User user);
}
