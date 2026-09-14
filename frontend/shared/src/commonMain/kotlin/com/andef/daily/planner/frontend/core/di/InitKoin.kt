package com.andef.daily.planner.frontend.core.di

import com.andef.daily.planner.frontend.app.AppViewModel
import com.andef.daily.planner.frontend.core.domain.HasSessionUseCase
import com.andef.daily.planner.frontend.feature.auth.data.repositories.AuthRepositoryImpl
import com.andef.daily.planner.frontend.feature.auth.domain.repositories.AuthRepository
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.ChangePasswordUseCase
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.LoginUseCase
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.LogoutUseCase
import com.andef.daily.planner.frontend.feature.auth.domain.usecases.RegisterUseCase
import com.andef.daily.planner.frontend.feature.auth.presentation.AuthViewModel
import com.andef.daily.planner.frontend.feature.planner.data.repositories.EventRepositoryImpl
import com.andef.daily.planner.frontend.feature.planner.domain.repositories.EventRepository
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.CreateEventUseCase
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.DeleteEventUseCase
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.GetEventsUseCase
import com.andef.daily.planner.frontend.feature.planner.domain.usecases.UpdateEventUseCase
import com.andef.daily.planner.frontend.feature.planner.presentation.PlannerViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { ChangePasswordUseCase(get()) }
    factory { HasSessionUseCase(get()) }
    factory { LogoutUseCase(get()) }
    viewModelOf(::AuthViewModel)
}

private val appModule = module {
    viewModelOf(::AppViewModel)
}

private val eventModule = module {
    single<EventRepository> { EventRepositoryImpl(get(), get()) }
    factory { GetEventsUseCase(get()) }
    factory { CreateEventUseCase(get()) }
    factory { UpdateEventUseCase(get()) }
    factory { DeleteEventUseCase(get()) }
    viewModelOf(::PlannerViewModel)
}

/**
 * Запускает контейнер зависимостей
 */
fun initKoin() {
    startKoin {
        modules(networkModule(), storageModule(), authModule, eventModule, appModule)
    }
}
