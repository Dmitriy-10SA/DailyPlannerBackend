package com.andef.daily.planner.frontend.core.domain

import com.andef.daily.planner.frontend.feature.auth.domain.repositories.AuthRepository

/**
 * Проверяет наличие сессии
 */
class HasSessionUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Boolean = repository.hasSession()
}