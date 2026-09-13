package com.andef.daily.planner.backend.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Пользователь
 */
@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
public class User {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * Логин
     */
    @Column(nullable = false, unique = true, length = 100)
    @Getter
    private String login;

    /**
     * Пароль
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    @Getter
    @Setter
    private String password;
}
