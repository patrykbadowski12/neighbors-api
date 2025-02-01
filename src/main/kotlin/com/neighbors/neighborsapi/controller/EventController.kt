package com.neighbors.neighborsapi.controller

import com.neighbors.neighborsapi.controller.model.CreateEventRequest
import com.neighbors.neighborsapi.controller.model.GetEventsResponse
import com.neighbors.neighborsapi.model.Event
import com.neighbors.neighborsapi.service.EventService
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EventController(
    private val eventService: EventService,
    private val pagedAssembler: PagedResourcesAssembler<GetEventsResponse>,
) {
    @GetMapping("/events")
    fun getEvents(pageable: Pageable) =
        pagedAssembler.toModel(
            eventService
                .getEvents(pageable)
                .map { mapEventToDto(it) }
                .map { it.add(linkTo<EventController> { getEvent(it.id) }.withSelfRel()) },
        )

    @GetMapping("/events/{id}")
    fun getEvent(
        @PathVariable id: Long,
    ) = eventService
        .getEvent(id)

    @PostMapping("/events")
    fun createEvent(
        @RequestBody @Valid request: CreateEventRequest,
    ) {
        eventService.createEvent(request)
    }

    private fun mapEventToDto(event: Event) =
        GetEventsResponse(
            id = event.id!!,
            name = event.name,
            description = event.description,
            type = event.type,
            dateFrom = event.dateFrom,
            dateTo = event.dateTo,
            maxParticipants = event.maxParticipants,
            latitude = event.latitude,
            longitude = event.longitude,
        )
}
