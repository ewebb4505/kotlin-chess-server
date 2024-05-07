package com.example.controllers

import com.example.models.UserId
import com.example.models.*
import com.example.services.UserService
import com.google.gson.Gson
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.cancel
import org.koin.ktor.ext.inject

fun Route.playerPoolController() {
    val userService by inject<UserService>()

    webSocket("/players_waiting_for_game") {
        println("Adding user!")
        // 1. on connection send the new user all the players that are in the pool and are ready to play.
        send("hello")
        call.respond("connected")
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            println("made it!")
            val receivedText = frame.readText()
            val playerConnection =  Gson().fromJson(receivedText, PlayerPoolRequest::class.java)
            val userId = UserId(playerConnection.userId)
            if (userService.isValidUserId(userId)) {
                when(playerConnection.status) {
                    PlayerPoolStatus.READY_TO_PLAY -> {
                        // add player to pool of players ready to play chess
                        PlayerPoolManager.addPlayer(this, UserId(playerConnection.userId), PlayerPoolStatus.READY_TO_PLAY)
                        println("${playerConnection.userId} with status: ${playerConnection.status} joined!")
                        send(Gson().toJson(PlayerPoolManager.toDictionary()))
                    }

                    PlayerPoolStatus.REQUESTED_TO_PLAY -> {
                        // player has been requested to play another player in the pool.

                    }

                    PlayerPoolStatus.REQUESTING_TO_PLAY -> {
                        // player has requested to play another player in the pool. Send that user a notification that they have been request to play
                        // first make sure the user making the request is already in this pool.
                        // next make sure the userId they are requesting is still in the pool.
                        // finally change the status of this user to REQUESTED_TO_PLAY and send the notification
                        println("GOT A REQUEST FROM A PLAYER")
                        val playerRequesting = PlayerPoolManager.getPlayerConnection(userId)
                        val playerRequested = PlayerPoolManager.getPlayerConnection(UserId(playerConnection.requestToPlayerUserId ?: ""))

                        if (playerRequesting != null && playerRequested != null && playerRequesting != playerRequested) {
                            playerRequesting.status = PlayerPoolStatus.REQUESTING_TO_PLAY
                            playerRequested.status = PlayerPoolStatus.REQUESTED_TO_PLAY

                            playerRequested.session.send("Requested To Play from ${playerRequesting.userId}!")
                            playerRequesting.session.send("Successful Request to ${playerRequested.userId}")

                            playerRequested.session.send(Gson().toJson(PlayerPoolManager.toDictionary()).toString())
                            playerRequesting.session.send(Gson().toJson(PlayerPoolManager.toDictionary()).toString())
                            // create pending game request
                            val pendingGameRequest = PendingGameRequest(playerRequesting.userId, playerRequested.userId)
                            PendingGamesManager.pendingGames[pendingGameRequest.id] = pendingGameRequest

                            playerRequested.session.send("")
                        } else {
                            println("Bad Request")
                            cancel("Not a good request")
                        }
                    }
                }
            } else {
                cancel("Not a good user id")
            }
        }
    }
}