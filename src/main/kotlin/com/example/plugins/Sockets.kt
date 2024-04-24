package com.example.plugins

import com.example.UserId
import com.example.UserRepo
import com.example.UserService
import com.google.gson.Gson
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.collections.*
import io.ktor.websocket.*
import io.ktor.websocket.serialization.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.*
import kotlin.collections.LinkedHashSet

import kotlinx.serialization.json.*
import org.koin.ktor.ext.inject
import java.time.LocalDateTime

fun Application.configureSockets() {
    val userService by inject<UserService>()
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
    routing {
        webSocket("/game{gameId}") { // websocketSession
            val player1 = call.request.queryParameters["player1"]
            //  val player2 = call.request.queryParameters["player2"]

//            for (frame in incoming) {
//                if (frame is Frame.Text) {
//                    val text = frame.readText()
//                    outgoing.send(Frame.Text("YOU SAID: $text"))
//                    if (text.equals("bye", ignoreCase = true)) {
//                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
//                    }
//                }
//            }
        }

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
                            send(Gson().toJson(PlayerPoolManager.toDictionary()).toString())
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
}

class PlayerConnection(
    val session: DefaultWebSocketSession,
    val userId: UserId,
    var status: PlayerPoolStatus
) {
    companion object {
        val lastId = AtomicInteger(0)
    }
}

@Serializable
data class PlayerPoolRequest(
    val userId: String,
    val status: PlayerPoolStatus,
    val requestToPlayerUserId: String? = null,
    val requestFromPlayerUserId: String? = null
)

@Serializable
data class PlayerPoolGlobalResponse(val usersAndStatus: Map<UserId, PlayerPoolStatus>)

data class PendingGameRequest(
    val requested: UserId,
    val requester: UserId
) {
    val id: UUID = UUID.randomUUID()
    val timeCreated: LocalDateTime = LocalDateTime.now()
    val timeExpires: LocalDateTime
        get() = timeCreated.plusSeconds(30)
}

@Serializable
enum class PlayerPoolStatus {
    @SerialName("ready_to_play")
    READY_TO_PLAY,

    @SerialName("requesting_to_play")
    REQUESTING_TO_PLAY,

    @SerialName("requested_to_play")
    REQUESTED_TO_PLAY
}

enum class PlayerRequest {
    DECLINE, ACCEPT
}

object PlayerPoolManager {
    val playersWaitingForGameConnection: MutableSet<PlayerConnection> = Collections.synchronizedSet<PlayerConnection>(LinkedHashSet())

    fun getPlayerStatus(userId: UserId): PlayerPoolStatus? {
        val status: PlayerPoolStatus? = try { playersWaitingForGameConnection.first { it.userId == userId }.status  } catch (e: Exception) { null }
        return status
    }

    fun addPlayer(session: DefaultWebSocketSession, userId: UserId, status: PlayerPoolStatus): Boolean {
        val player = PlayerConnection(session, userId, status)
        if (playersWaitingForGameConnection.contains(player)) {
            return false
        } else {
            playersWaitingForGameConnection.add(player)
            return true
        }
    }

    fun getPlayerConnection(userId: UserId): PlayerConnection? {
        val connection: PlayerConnection? = try {
            playersWaitingForGameConnection.first { it.userId == userId }
        } catch (e: Exception) {
            null
        }
        return connection
    }

    fun toDictionary(): Map<UserId, PlayerPoolStatus> {
        val map = mutableMapOf<UserId, PlayerPoolStatus>()
        playersWaitingForGameConnection.forEach { map[it.userId] = it.status }
        return map
    }
}

object PendingGamesManager {
    val pendingGames: MutableMap<UUID, PendingGameRequest> = mutableMapOf<UUID, PendingGameRequest>()
}