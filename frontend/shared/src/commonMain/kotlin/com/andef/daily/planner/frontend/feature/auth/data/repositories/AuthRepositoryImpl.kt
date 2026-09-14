package com.andef.daily.planner.frontend.feature.auth.data.repositories

import com.andef.daily.planner.frontend.core.data.ApiException
import com.andef.daily.planner.frontend.core.data.ErrorResponseDto
import com.andef.daily.planner.frontend.core.platform.storage.TokenStorage
import com.andef.daily.planner.frontend.feature.auth.data.dtos.AuthResponseDto
import com.andef.daily.planner.frontend.feature.auth.data.dtos.ChangePasswordDto
import com.andef.daily.planner.frontend.feature.auth.data.dtos.LoginDto
import com.andef.daily.planner.frontend.feature.auth.data.dtos.RegisterDto
import com.andef.daily.planner.frontend.feature.auth.domain.repositories.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

/**
 * Реализация репозитория аутентификации
 */
class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun register(login: String, password: String) {
        val response = httpClient.post("/api/v1/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterDto(login = login, password = password))
        }
        saveToken(response)
    }

    override suspend fun login(login: String, password: String) {
        val response = httpClient.post("/api/v1/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginDto(login = login, password = password))
        }
        saveToken(response)
    }

    override suspend fun changePassword(
        login: String,
        newPassword: String
    ) {
        val response = httpClient.patch("/api/v1/auth/change-password") {
            contentType(ContentType.Application.Json)
            setBody(ChangePasswordDto(login = login, newPassword = newPassword))
        }
        saveToken(response)
    }

    override fun hasSession(): Boolean = !tokenStorage.getToken().isNullOrBlank()

    override fun logout() = tokenStorage.removeToken()

    /**
     * Проверяет ответ и сохраняет полученный токен
     */
    private suspend fun saveToken(response: HttpResponse) {
        if (!response.status.isSuccess()) {
            val message = runCatching { response.body<ErrorResponseDto>().message }
                .getOrDefault("Не удалось выполнить запрос")
            throw ApiException(message)
        }

        tokenStorage.saveToken(response.body<AuthResponseDto>().jwt)
    }
}