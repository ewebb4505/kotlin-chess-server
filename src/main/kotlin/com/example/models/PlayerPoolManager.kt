package com.example.models

import io.ktor.websocket.*
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.NotNull
import java.util.*
import kotlin.collections.LinkedHashSet

object PlayerPoolManager {
    val playersWaitingForGameConnection: MutableSet<PlayerConnection> = Collections.synchronizedSet<PlayerConnection>(LinkedHashSet())

    private val players: Array<String> by lazy {
        playersWaitingForGameConnection.map { it.userId.id }.toTypedArray()
    }

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

    fun toDictionary(): Map<String, PlayerPoolStatus> {
        val map = mutableMapOf<String, PlayerPoolStatus>()
        playersWaitingForGameConnection.forEach { map[it.userId.id] = it.status }
        return map
    }

    fun createBroadcast(): PlayersBroadcastResponse = PlayersBroadcastResponse(players)
}

@Serializable
data class PlayersBroadcastResponse(
    val players: Array<String>
)