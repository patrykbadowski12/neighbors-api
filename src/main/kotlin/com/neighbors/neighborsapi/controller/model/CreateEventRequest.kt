package com.neighbors.neighborsapi.controller.model

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateEventRequest(
    @field:NotEmpty(message = "Event name cannot be empty")
    @field:Size(min = 5, max = 30, message = "Name of event should have 5-30 characters")
    val name: String,
    @field:NotEmpty(message = "Description cannot be empty")
    @field:Size(min = 10, max = 150, message = "Event should have 10-30 characters")
    val description: String,
    @field:NotNull(message = "Event start date cannot be null")
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val maxParticipants: Int,
    @field:NotNull(message = "Event latitude cannot be null")
    val latitude: Double,
    @field:NotNull(message = "Event longitude cannot be null")
    val longitude: Double,
    val placeName: String,
    val placeId: String,
)
