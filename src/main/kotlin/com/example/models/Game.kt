package com.example.models

import com.example.Utils.GameUtils
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.properties.Delegates

typealias Board = MutableMap<BoardSpot, Piece?>
typealias Pieces = MutableMap<Piece, MutableList<BoardSpot>?>
typealias Moves = ArrayDeque<Move>

data class Game(val requested: UserId, val requester: UserId) {
    val id: UUID = UUID.randomUUID()
    val timeCreated: LocalDateTime = LocalDateTime.now()
    val timeExpires: LocalDateTime
        get() = timeCreated.plusSeconds(30)

    var bothPlayerHaveJoined: Boolean = false
    val chessBoard = ChessBoard()

}

data class ChessBoard (
    val board: Board = GameUtils.setBoard(),
    val whitePieces: Pieces = GameUtils.setPiecesForWhite(),
    val blackPieces: Pieces = GameUtils.setPiecesForBlack(),
    var moves: Moves = ArrayDeque()
)

fun Board.find(piece: Piece): BoardSpot? {
    this.keys.forEach {
        if (this[it] == piece) {
            return it
        }
    }
    return null
}

fun Pieces.squaresAttackedBy(piece: Piece, at: BoardSpot, isWhitePiece: Boolean = true): Set<BoardSpot> {
    when(piece) {
        Piece.Q -> {
            val attackedSpots = BoardSpot.entries.toMutableList()
            attackedSpots.remove(at)
            return attackedSpots.toSet()
        }

        Piece.K -> {
            return at.getAdjacent()
        }

        Piece.R -> {
            val row = at.row()
            val file = at.file()

            val spotsOnRow = mutableSetOf (
                BoardSpot.construct(row, "a"),
                BoardSpot.construct(row, "b"),
                BoardSpot.construct(row, "c"),
                BoardSpot.construct(row, "d"),
                BoardSpot.construct(row, "e"),
                BoardSpot.construct(row, "f"),
                BoardSpot.construct(row, "g"),
                BoardSpot.construct(row, "h")
            ).filterNotNull().toMutableSet()

            val spotsOnFile = mutableSetOf (
                BoardSpot.construct(1, file),
                BoardSpot.construct(2, file),
                BoardSpot.construct(3, file),
                BoardSpot.construct(4, file),
                BoardSpot.construct(5, file),
                BoardSpot.construct(6, file),
                BoardSpot.construct(7, file),
                BoardSpot.construct(8, file),
            ).filterNotNull().toMutableSet()

            spotsOnRow.remove(at)
            spotsOnFile.remove(at)
            return (spotsOnFile + spotsOnRow).toSet()
        }

        Piece.B -> {
            val attackingSpots: MutableSet<BoardSpot> = mutableSetOf()

            for (i in 1..8) {
                at.next(up = i, left = i)?.let { attackingSpots.add(it) }
                at.next(up = i, right = i)?.let { attackingSpots.add(it) }
                at.next(down = i, left = i)?.let { attackingSpots.add(it) }
                at.next(down = i, right = i)?.let { attackingSpots.add(it) }
            }

            return attackingSpots
        }

        Piece.N -> {
            return setOfNotNull(
                at.next(up = 2, right = 1),
                at.next(up = 2, left = 1),
                at.next(up = 1, right = 2),
                at.next(up = 1, left = 2),
                at.next(down = 2, right = 1),
                at.next(down = 2, left = 1),
                at.next(down = 1, right = 2),
                at.next(down = 1, left = 2),
            )
        }
        Piece.P -> {
            return if (isWhitePiece) {
                setOfNotNull(
                    at.next(up = 1, right = 1),
                    at.next(up = 1, left = 1),
                )
            } else {
                setOfNotNull(
                    at.next(down = 1, right = 1),
                    at.next(down = 1, left = 1),
                )
            }
        }
    }
}