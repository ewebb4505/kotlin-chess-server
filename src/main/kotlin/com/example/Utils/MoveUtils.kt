package com.example.Utils

import com.example.models.Board
import com.example.models.BoardSpot
import com.example.models.Move
import com.example.models.Piece

object MoveUtils {
    private const val KING_SIDE_CASTLE = "0-0"
    private const val QUEEN_SIDE_CASTLE = "0-0-0"
    private const val NUMBER_MOVE_DELIM = "_"
    private const val PAWN_PROMOTION = "/"
    private const val OFFERED_DRAW = "(=)"
    private const val EN_PASSANT = "e.p."
    private const val TAKE = "x"
    private const val CHECKMATE = "#"
    private const val CHECK = "+"

    fun parseMove(input: String): Move? {
        var move: Move? = null
        val numberMoveSplit = input.split(NUMBER_MOVE_DELIM)
        // there should only ever be one '_' in the input
        if (numberMoveSplit.count() > 2) return null

        // the first value before the '_' should only be an int
        val moveNumber = numberMoveSplit[0].toIntOrNull()
        if (moveNumber == null) return null

        val moveAction = numberMoveSplit[1]
        if (moveAction.isEmpty()) return null
        // check for castles, promotions, offered draws,
        castleOrOfferedDraw(moveNumber, moveAction, input)?.let {
            move = it
            return move
        }

        // determine the piece and where it's trying to move
        extractPieceAndDestinationSquare(moveNumber, moveAction, input)?.let { move = it }
        return move
    }

    private fun castleOrOfferedDraw(moveNumber: Int, input: String, initialMove: String): Move? {
        return when (input) {
            KING_SIDE_CASTLE -> Move(initialMove).also {
                it.number = moveNumber
                it.isCastlingKingSide = true
            }

            QUEEN_SIDE_CASTLE -> Move(initialMove).also {
                it.number = moveNumber
                it.isCastlingQueenSide = true
            }

            OFFERED_DRAW -> Move(initialMove).also {
                it.number = moveNumber
                it.isOfferingDraw = true
            }

            else -> null
        }
    }

    private fun extractPieceAndDestinationSquare(moveNumber: Int, input: String, initialMove: String): Move? {
        var isTake = false
        var isPawnPromotion = false
        var isCheck = false
        var isCheckmate = false
        var isEnPassant = false
        var promotionPiece: Piece? = null
        var move: Move? = null

        //first get the piece that is wanting to move. first character should be a piece
        val piece = try { Piece.valueOf(input.first().toString()) } catch (e: IllegalArgumentException) { null }
        println("for move# $moveNumber : $input Piece ${piece.toString()} was found")

        if (piece == null) return null
        // pop the first character from the string
        var moveWithoutPiece = input.drop(1)

        // determine if this is a take. if so record it. if not move on
        if (moveWithoutPiece.isTake()) {
            isTake = true
            moveWithoutPiece = moveWithoutPiece.removeTake()
            println("for move# $moveNumber : $input , looks like $piece is trying to take another piece")
        }

        if (moveWithoutPiece.isCheckmate()) {
            isCheckmate = true
            moveWithoutPiece = moveWithoutPiece.removeCheckmate()
            println("for move# $moveNumber : $input , looks like the other user is in checkmate!")
        }

        if (moveWithoutPiece.isCheck() && isCheckmate == false) {
            isCheck = true
            moveWithoutPiece = moveWithoutPiece.removeCheck()
            println("for move# $moveNumber : $input , looks like the other user is in check")
        }

        if (moveWithoutPiece.isPawnPromotion()) {
            isPawnPromotion = true
            val promoSplit = moveWithoutPiece.split(PAWN_PROMOTION)
            moveWithoutPiece = promoSplit.first()
            promotionPiece = try { Piece.valueOf(promoSplit[1]) } catch (e: IllegalArgumentException) { null }
            println("for move# $moveNumber : $input , looks like a pawn is getting a promotion")
        }

        if (moveWithoutPiece.isEnPassant()) {
            isEnPassant = true
            moveWithoutPiece = moveWithoutPiece.removeEnPassant()
            println("for move# $moveNumber : $input , EN PASSANT")
        }

        // determine the square where the user wants to move
        val square = try { BoardSpot.valueOf(moveWithoutPiece) } catch (e: IllegalArgumentException) { null }
        println("for move# $moveNumber : $input , found the destination square to be $square")

        move = Move(initialMove).also {
            it.number = moveNumber
            it.isTaking = isTake
            it.piece = piece
            it.isPawnPromotion = isPawnPromotion
            it.promotionPiece = promotionPiece
            it.isCheck = isCheck
            it.isCheckmate = isCheckmate
            it.isEnPassant = isEnPassant
            it.destinationSpot = square
        }

        return move
    }

    private fun String.isTake(): Boolean = this.contains(TAKE)
    private fun String.removeTake(): String = this.removePrefix(TAKE)
    private fun String.isCheckmate(): Boolean = this.contains(CHECKMATE)
    private fun String.removeCheckmate(): String = this.removeSuffix(CHECKMATE)
    private fun String.isCheck(): Boolean = this.contains(CHECK)
    private fun String.removeCheck(): String = this.removeSuffix(CHECK)
    private fun String.isPawnPromotion(): Boolean = this.contains(PAWN_PROMOTION)
    private fun String.removePawnPromotion(): String = this.removeSuffix(PAWN_PROMOTION)
    private fun String.isEnPassant(): Boolean = this.contains(EN_PASSANT)
    private fun String.removeEnPassant(): String = this.removeSuffix(EN_PASSANT)
}