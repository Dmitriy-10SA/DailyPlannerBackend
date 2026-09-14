package com.andef.daily.planner.frontend.feature.auth.domain.usecases

import com.andef.daily.planner.frontend.feature.auth.domain.repositories.AuthRepository

/**
 * Выполняет вход
 */
class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(login: String, password: String) = repository.login(login, password)
}