package com.example.controllers

import com.example.services.UserService
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject

fun Route.gameController() {
    val userService by inject<UserService>()
    webSocket("/players_waiting_for_game_2") {
        val player1 = call.request.queryParameters["player1"]

    }
}