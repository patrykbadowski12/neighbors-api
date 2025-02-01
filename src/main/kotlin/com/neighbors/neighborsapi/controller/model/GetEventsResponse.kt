package com.neighbors.neighborsapi.controller.model

import com.neighbors.neighborsapi.model.EventStatus
import org.springframework.hateoas.RepresentationModel
import java.time.LocalDateTime

class GetEventsResponse(
    val id: Long,
    val name: String,
    val description: String,
    val dateFrom: LocalDateTime,
    val dateTo: LocalDateTime?,
    val maxParticipants: Int?,
    val type: EventStatus,
    val latitude: Double,
    val longitude: Double,
) : RepresentationModel<GetEventsResponse>()
