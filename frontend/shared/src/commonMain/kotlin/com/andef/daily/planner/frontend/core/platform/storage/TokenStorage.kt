package com.andef.daily.planner.frontend.core.platform.storage

/**
 * Хранилище JWT-токена
 */
interface TokenStorage {

    /**
     * Возвращает сохранённый токен
     */
    fun getToken(): String?

    /**
     * Сохраняет токен
     */
    fun saveToken(token: String)

    /**
     * Удаляет токен
     */
    fun removeToken()
}
