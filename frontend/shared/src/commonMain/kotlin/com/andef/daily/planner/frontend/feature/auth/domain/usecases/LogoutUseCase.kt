package com.andef.daily.planner.frontend.feature.auth.domain.usecases

import com.andef.daily.planner.frontend.feature.auth.domain.repositories.AuthRepository

/**
 * Завершает сессию
 */
class LogoutUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.logout()
}
