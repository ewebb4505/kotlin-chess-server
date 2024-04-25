package com.example.models

import io.ktor.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class PlayerConnection(
    val session: DefaultWebSocketSession,
    val userId: UserId,
    var status: PlayerPoolStatus
) {
    companion object {
        val lastId = AtomicInteger(0)
    }
}