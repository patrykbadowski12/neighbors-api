package com.neighbors.neighborsapi.service

import com.neighbors.neighborsapi.controller.model.CreateEventRequest
import com.neighbors.neighborsapi.model.Event
import com.neighbors.neighborsapi.model.EventStatus
import com.neighbors.neighborsapi.repository.EventRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class EventService(
    private val eventRepository: EventRepository,
) {
    fun getEvents(pageable: Pageable) = eventRepository.findAll(pageable)

    fun createEvent(request: CreateEventRequest) {
        Event(
            id = null,
            name = request.name,
            description = request.description,
            dateFrom = request.dateFrom,
            dateTo = request.dateTo,
            maxParticipants = request.maxParticipants,
            type = EventStatus.CREATED,
            latitude = request.latitude,
            longitude = request.longitude,
        ).run {
            eventRepository.save(this)
        }
    }

    fun getEvent(id: Long) =
        eventRepository
            .findById(id)
            .getOrElse { throw Exception("Event not found") }
}
