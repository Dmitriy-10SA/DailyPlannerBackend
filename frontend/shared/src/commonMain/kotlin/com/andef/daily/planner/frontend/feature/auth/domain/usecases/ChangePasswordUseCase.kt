package com.andef.daily.planner.frontend.feature.auth.domain.usecases

import com.andef.daily.planner.frontend.feature.auth.domain.repositories.AuthRepository

/**
 * Выполняет смену пароля
 */
class ChangePasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(login: String, password: String) = repository.changePassword(login, password)
}