package com.andef.daily.planner.frontend.core.di

import org.koin.core.module.Module

/**
 * Возвращает сетевой модуль платформы
 */
expect fun networkModule(): Module

/**
 * Возвращает модуль хранилища платформы
 */
expect fun storageModule(): Module
