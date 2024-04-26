package com.example.models

import com.example.Utils.GameUtils
import java.time.LocalDateTime
import java.util.*

typealias Board = MutableMap<BoardSpot, Piece?>

data class Game(val requested: UserId, val requester: UserId) {
    val id: UUID = UUID.randomUUID()
    val timeCreated: LocalDateTime = LocalDateTime.now()
    val timeExpires: LocalDateTime
        get() = timeCreated.plusSeconds(30)

    var bothPlayerHaveJoined: Boolean = false
    var board: Board = GameUtils.setBoard()
    var moves: Array<Move> = arrayOf()
}