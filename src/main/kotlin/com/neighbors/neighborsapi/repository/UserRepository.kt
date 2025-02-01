package com.neighbors.neighborsapi.repository

import com.neighbors.neighborsapi.model.User
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}
