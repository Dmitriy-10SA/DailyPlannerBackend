package com.andef.daily.planner.backend.data.repositories;

import com.andef.daily.planner.backend.data.entities.Event;
import com.andef.daily.planner.backend.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для {@link Event}
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Находит мероприятие по идентификатору и пользователю
     *
     * @param id   Идентификатор мероприятия
     * @param user Пользователь
     * @return Найденное мероприятие
     */
    Optional<Event> findByIdAndUser(Long id, User user);

    /**
     * Возвращает мероприятия пользователя за выбранный день с учётом поисковой строки
     *
     * @param user       Пользователь
     * @param day        Выбранный день
     * @param searchText Поисковая строка или пустая строка
     * @return Мероприятия по времени начала
     */
    @Query("""
            SELECT event
            FROM Event event
            WHERE event.user = :user
              AND CAST(event.startsAt AS LocalDate) = :day
              AND (
                  :searchText = ''
                  OR LOWER(event.title) LIKE LOWER(CONCAT('%', :searchText, '%'))
                  OR LOWER(event.location) LIKE LOWER(CONCAT('%', :searchText, '%'))
              )
            ORDER BY event.startsAt
            """)
    List<Event> findAllByUserAndDayAndSearchText(
            @Param("user") User user,
            @Param("day") LocalDate day,
            @Param("searchText") String searchText
    );
}
