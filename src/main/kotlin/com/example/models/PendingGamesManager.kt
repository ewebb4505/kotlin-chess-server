package com.example.models

import java.util.*

object PendingGamesManager {
    val pendingGames: MutableMap<UUID, PendingGameRequest> = mutableMapOf<UUID, PendingGameRequest>()
}