package com.andef.daily.planner.frontend.feature.auth.domain.repositories

/**
 * Репозиторий аутентификации
 */
interface AuthRepository {

    /**
     * Регистрирует пользователя
     */
    suspend fun register(login: String, password: String)

    /**
     * Выполняет вход пользователя
     */
    suspend fun login(login: String, password: String)

    /**
     * Изменяет пароль пользователя
     */
    suspend fun changePassword(login: String, newPassword: String)

    /**
     * Проверяет наличие пользовательской сессии
     */
    fun hasSession(): Boolean

    /**
     * Завершает пользовательскую сессию
     */
    fun logout()
}