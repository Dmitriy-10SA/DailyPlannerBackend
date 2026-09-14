package com.andef.daily.planner.frontend.feature.planner.data.repositories

import com.andef.daily.planner.frontend.core.data.ApiException
import com.andef.daily.planner.frontend.core.data.ErrorResponseDto
import com.andef.daily.planner.frontend.core.platform.storage.TokenStorage
import com.andef.daily.planner.frontend.feature.planner.data.dtos.CreateEventDto
import com.andef.daily.planner.frontend.feature.planner.data.dtos.EventDto
import com.andef.daily.planner.frontend.feature.planner.data.dtos.UpdateEventDto
import com.andef.daily.planner.frontend.feature.planner.domain.model.Event
import com.andef.daily.planner.frontend.feature.planner.domain.repositories.EventRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

/**
 * Реализация репозитория мероприятий
 */
class EventRepositoryImpl(
    private val httpClient: HttpClient,
    private val tokenStorage: TokenStorage
) : EventRepository {

    override suspend fun getEvents(day: String, searchText: String?): List<Event> {
        val response = httpClient.get("/api/v1/events") {
            authorize()
            parameter("day", day)
            searchText?.takeIf(String::isNotBlank)?.let { parameter("searchText", it) }
        }
        checkResponse(response)
        return response.body<List<EventDto>>().map { it.toDomain() }
    }

    override suspend fun create(
        title: String,
        location: String,
        startsAt: String,
        endsAt: String
    ): Event {
        val response = httpClient.post("/api/v1/events") {
            authorize()
            contentType(ContentType.Application.Json)
            setBody(CreateEventDto(title, location, startsAt, endsAt))
        }
        checkResponse(response)
        return response.body<EventDto>().toDomain()
    }

    override suspend fun update(
        id: Long,
        title: String,
        location: String,
        startsAt: String,
        endsAt: String
    ): Event {
        val response = httpClient.put("/api/v1/events/$id") {
            authorize()
            contentType(ContentType.Application.Json)
            setBody(UpdateEventDto(title, location, startsAt, endsAt))
        }
        checkResponse(response)
        return response.body<EventDto>().toDomain()
    }

    override suspend fun delete(id: Long) {
        checkResponse(
            httpClient.delete("/api/v1/events/$id") {
                authorize()
            }
        )
    }

    /**
     * Добавляет JWT-токен в запрос
     */
    private fun HttpRequestBuilder.authorize() {
        bearerAuth(tokenStorage.getToken().orEmpty())
    }

    /**
     * Проверяет ответ API
     */
    private suspend fun checkResponse(response: HttpResponse) {
        if (response.status.isSuccess()) return

        val message = runCatching { response.body<ErrorResponseDto>().message }
            .getOrDefault("Не удалось выполнить запрос")
        throw ApiException(message)
    }

    /**
     * Преобразует DTO в модель
     */
    private fun EventDto.toDomain() = Event(id, title, location, startsAt, endsAt)
}