package com.neighbors.neighborsapi.repository

import com.neighbors.neighborsapi.model.Event
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface EventRepository : CrudRepository<Event, Long> {
    fun findAll(pageable: Pageable): Page<Event>
}
