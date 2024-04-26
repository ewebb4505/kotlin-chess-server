package com.example.Utils

import com.example.models.Board
import com.example.models.BoardSpot
import com.example.models.Move
import com.example.models.Piece

object MoveUtils {
    const val KING_SIDE_CASTLE = "0-0"
    const val QUEEN_SIDE_CASTLE = "0-0-0"
    const val NUMBER_MOVE_DELIM = "_"
    const val PAWN_PROMOTION = "/"
    const val OFFERED_DRAW = "(=)"
    const val EN_PASSANT = "e.p."
    const val TAKE = "x"
    const val CHECKMATE = "#"

    fun parseMove(input: String): Move? {
        var move: Move? = null
        val numberMoveSplit = input.split(NUMBER_MOVE_DELIM)
        // there should only ever be one '_' in the input
        if (numberMoveSplit.count() > 2) return null

        // the first value before the '_' should only be an int
        val moveNumber = numberMoveSplit[0].toIntOrNull()
        if (moveNumber == null) return null

        val input = numberMoveSplit[1]
        if (input.isEmpty()) return null
        // check for castles, promotions, offered draws,
        castleOrOfferedDraw(moveNumber, input)?.let { move = it }

        // determine the piece and where it's trying to move
        extractPieceAndDestinationSquare(moveNumber, input)?.let { move = it }
        return move
    }

    private fun castleOrOfferedDraw(moveNumber: Int, input: String): Move? {
        return when (input) {
            KING_SIDE_CASTLE -> Move(input).also {
                it.number = moveNumber
                it.isCastlingKingSide = true
            }

            QUEEN_SIDE_CASTLE -> Move(input).also {
                it.number = moveNumber
                it.isCastlingQueenSide = true
            }

            OFFERED_DRAW -> Move(input).also {
                it.number = moveNumber
                it.isOfferingDraw = true
            }

            else -> null
        }
    }

    private fun extractPieceAndDestinationSquare(moveNumber: Int, input: String): Move? {
        var isTake = false
        var isPawnPromotion = false
        var isCheck = false
        var isCheckmate = false
        var isEnPassant = false
        var move: Move? = null

        //first get the piece that is wanting to move. first character should be a piece
        val piece = try { Piece.valueOf(input.first().toString()) } catch (e: IllegalArgumentException) { null }
        if (piece == null) return null
        // pop the first character from the string
        var moveWithoutPiece = input.drop(1)

        // determine if this is a take. if so record it. if not move on
        if (moveWithoutPiece.isTake()) {
            isTake = true
            moveWithoutPiece = moveWithoutPiece.removeTake()
        }

        // TODO missing a couple things here
        // TODO : check for checkmate at the end of the move
        // TODO : check for check at the end of the move
        // TODO : check for pawn promotion
        // TODO : check for en passant capture

        // determine the square where the user wants to move
        val sqaure = try { Piece.valueOf(moveWithoutPiece) } catch (e: IllegalArgumentException) { null }

        move = Move(input).also {
            it.number = moveNumber
            it.isTaking = isTake
            it.piece = piece
            it.isPawnPromotion = isPawnPromotion
            // TODO: add other properties here from above
        }
        return move
    }

    private fun String.isTake(): Boolean = this.contains(TAKE)
    private fun String.removeTake(): String = this.removeSuffix(TAKE)
}