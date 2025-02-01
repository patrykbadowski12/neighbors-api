package com.neighbors.neighborsapi.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    val email: String,
    val name: String,
    val givenName: String,
    val familyName: String,
    val profilePicture: String,
)
