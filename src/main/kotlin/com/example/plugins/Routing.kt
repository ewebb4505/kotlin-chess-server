package com.example.plugins

import com.example.controllers.games
import com.example.controllers.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRoute()
        games()
    }
}

