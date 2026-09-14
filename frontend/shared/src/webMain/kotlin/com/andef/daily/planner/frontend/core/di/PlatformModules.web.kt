package com.andef.daily.planner.frontend.core.di

import com.andef.daily.planner.frontend.core.platform.storage.TokenStorage
import com.andef.daily.planner.frontend.core.platform.storage.WebTokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun networkModule(): Module = module {
    single {
        HttpClient(Js) {
            expectSuccess = false

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }

            install(Logging) {
                level = LogLevel.INFO
            }
        }
    }
}

actual fun storageModule(): Module = module {
    single<TokenStorage> { WebTokenStorage() }
}
