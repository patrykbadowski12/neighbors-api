package com.neighbors.neighborsapi.controller

import com.neighbors.neighborsapi.controller.model.CreateEventRequest
import com.neighbors.neighborsapi.controller.model.GetEventsResponse
import com.neighbors.neighborsapi.model.Event
import com.neighbors.neighborsapi.service.EventService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = ["*"])
class EventController(
    private val eventService: EventService,
    private val pagedAssembler: PagedResourcesAssembler<GetEventsResponse>,
) {
    private val logger: Logger = LoggerFactory.getLogger(EventController::class.java)

    @GetMapping("/events")
    fun getEvents(pageable: Pageable): PagedModel<EntityModel<GetEventsResponse>> {
        val eventsPage = eventService.getEvents(pageable)

        val eventDtos =
            eventsPage.map {
                logger.info(it.toString())
                val dto = mapEventToDto(it)
                dto.add(linkTo<EventController> { getEvent(dto.id) }.withSelfRel())
            }
        return pagedAssembler.toModel(eventDtos)
    }

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
