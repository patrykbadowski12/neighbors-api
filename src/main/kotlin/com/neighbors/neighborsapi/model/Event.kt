package com.neighbors.neighborsapi.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val name: String,
    val description: String,
    val dateFrom: LocalDateTime,
    val dateTo: LocalDateTime?,
    val maxParticipants: Int?,
    val type: EventStatus,
    val latitude: Double,
    val longitude: Double,
)
