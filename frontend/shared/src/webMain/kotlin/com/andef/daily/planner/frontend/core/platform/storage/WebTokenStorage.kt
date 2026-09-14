package com.andef.daily.planner.frontend.core.platform.storage

import web.storage.localStorage

/**
 * Браузерное хранилище JWT-токена
 */
class WebTokenStorage : TokenStorage {

    override fun getToken(): String? = localStorage.getItem(TOKEN_KEY)

    override fun saveToken(token: String) {
        localStorage.setItem(TOKEN_KEY, token)
    }

    override fun removeToken() {
        localStorage.removeItem(TOKEN_KEY)
    }

    private companion object {
        const val TOKEN_KEY = "daily_planner_jwt"
    }
}
