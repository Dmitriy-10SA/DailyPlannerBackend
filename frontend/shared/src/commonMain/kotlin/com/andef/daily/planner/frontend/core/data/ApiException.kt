package com.andef.daily.planner.frontend.core.data

/**
 * Ошибка запроса к API
 *
 * @param message Сообщение сервера
 */
class ApiException(message: String) : RuntimeException(message)