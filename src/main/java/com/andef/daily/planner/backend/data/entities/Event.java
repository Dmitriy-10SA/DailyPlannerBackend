package com.andef.daily.planner.backend.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Мероприятие пользователя
 */
@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class Event {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Пользователь
     *
     * @see User
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Название
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * Место проведения
     */
    @Column(nullable = false, length = 300)
    private String location;

    /**
     * Дата и время начала
     */
    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    /**
     * Дата и время окончания
     */
    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;
}
