package com.neighbors.neighborsapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport
class NeighborsApiApplication

fun main(args: Array<String>) {
    runApplication<NeighborsApiApplication>(*args)
}
