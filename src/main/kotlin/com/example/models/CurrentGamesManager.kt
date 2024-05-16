package com.example.models

import io.ktor.websocket.*
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.LinkedHashSet

object CurrentGamesManager {
    private val currentGames: MutableSet<CurrentGame> = Collections.synchronizedSet<CurrentGame>(LinkedHashSet())

    fun findGameId(gameId: String): CurrentGame? = currentGames.find { it.gameId == gameId }
}

class CurrentGame(
    val gameId: String,
    val white: UserId,
    val black: UserId
) {
    private var whitePlayerSession: DefaultWebSocketSession? = null
    private var blackPlayerSession: DefaultWebSocketSession? = null

    var whiteIsConnected: Boolean = false
    var blackIsConnected: Boolean = false

    companion object {
        val lastId = AtomicInteger(0)
    }

    fun setUserConnection(id: UserId): Boolean {
        if (id == white) {
            whiteIsConnected = true
            return true
        } else if (id == black) {
            blackIsConnected = true
            return true
        } else {
            return false
        }
    }

    fun setWhitePlayerSession(session: DefaultWebSocketSession) {
        if (whitePlayerSession == null) {
            whitePlayerSession = session
        }
    }

    fun setBlackPlayerSession(session: DefaultWebSocketSession) {
        if (blackPlayerSession == null) {
            blackPlayerSession = session
        }
    }

}