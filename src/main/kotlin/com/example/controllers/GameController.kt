package com.example.controllers

import com.example.Utils.MoveUtils
import com.example.models.CurrentGamesManager
import com.example.models.PlayerPoolRequest
import com.example.models.UserId
import com.example.services.UserService
import com.google.gson.Gson
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Route.gameController() {
    // val userService by inject<UserService>()
    val gson = Gson()

    webSocket("/game/{gameId}") {
        val gameId = call.request.queryParameters["gameId"] ?: return@webSocket send("Can't find Game")
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            CurrentGamesManager.findGameId(gameId)?.let {
                val receivedText = frame.readText()

                // 1. determine if the message coming from the client is a request to join the game.
                //      if so get the userId and make sure that matches with either the white players userId or black players user id
                //          update the status of that player if so. if both players are joined send a new message with status as PLAYING
                //      if not remove the connection from that client.
                // 2 determine if the message is a white move or a black move.
                //      if so decode the move and make sure it's legal.
                //          if the move is legal send a message to the op player with the move and new status
                //      if it's not legal notify the user that it was a bad move.

                val playerConnected = gson.fromJson(receivedText, UserGameRequest::class.java)
                if (playerConnected.status == UserGameRequestStatus.JOINING) {
                    if (it.setUserConnection(playerConnected.userId)) {
                        if (it.black == playerConnected.userId) {
                            it.setBlackPlayerSession(this)
                        } else {
                            it.setWhitePlayerSession(this)
                        }
                    }
                } else if (playerConnected.status == UserGameRequestStatus.SENDING_MOVE) {
                    val move = playerConnected.move ?: return@webSocket send("Can't find move")
                    val parsedMove = MoveUtils.parseMove(move) ?: return@webSocket send("Can't parse move")

                }
            }
        }
    }
}

@Serializable
data class UserGameRequest(val userId: UserId, val status: UserGameRequestStatus, val move: String? = null)

@Serializable
enum class GameStatusResponse {
    @SerialName("waiting_for_op")
    WAITING_FOR_OP,

    @SerialName("playing")
    PLAYING,

    @SerialName("white_move")
    WHITE_MOVE,

    @SerialName("black_move")
    BLACK_MOVE
}

@Serializable
enum class UserGameRequestStatus {
    @SerialName("joining")
    JOINING,

    @SerialName("SENDING_MOVE")
    SENDING_MOVE,
}