package com.example.Utils

import com.example.models.*

object GameUtils {
    fun setBoard(): Board {
        val board: Board = mutableMapOf()
        for (spot in BoardSpot.entries) {
            when (spot) {
                BoardSpot.a8 -> board[spot] = Piece.R
                BoardSpot.b8 -> board[spot] = Piece.N
                BoardSpot.c8 -> board[spot] = Piece.B
                BoardSpot.d8 -> board[spot] = Piece.Q
                BoardSpot.e8 -> board[spot] = Piece.K
                BoardSpot.f8 -> board[spot] = Piece.B
                BoardSpot.g8 -> board[spot] = Piece.N
                BoardSpot.h8 -> board[spot] = Piece.R

                BoardSpot.a7 -> board[spot] = Piece.P
                BoardSpot.b7 -> board[spot] = Piece.P
                BoardSpot.c7 -> board[spot] = Piece.P
                BoardSpot.d7 -> board[spot] = Piece.P
                BoardSpot.e7 -> board[spot] = Piece.P
                BoardSpot.f7 -> board[spot] = Piece.P
                BoardSpot.g7 -> board[spot] = Piece.P
                BoardSpot.h7 -> board[spot] = Piece.P

                BoardSpot.a6 -> board[spot] = null
                BoardSpot.b6 -> board[spot] = null
                BoardSpot.c6 -> board[spot] = null
                BoardSpot.d6 -> board[spot] = null
                BoardSpot.e6 -> board[spot] = null
                BoardSpot.f6 -> board[spot] = null
                BoardSpot.g6 -> board[spot] = null
                BoardSpot.h6 -> board[spot] = null

                BoardSpot.a5 -> board[spot] = null
                BoardSpot.b5 -> board[spot] = null
                BoardSpot.c5 -> board[spot] = null
                BoardSpot.d5 -> board[spot] = null
                BoardSpot.e5 -> board[spot] = null
                BoardSpot.f5 -> board[spot] = null
                BoardSpot.g5 -> board[spot] = null
                BoardSpot.h5 -> board[spot] = null

                BoardSpot.a4 -> board[spot] = null
                BoardSpot.b4 -> board[spot] = null
                BoardSpot.c4 -> board[spot] = null
                BoardSpot.d4 -> board[spot] = null
                BoardSpot.e4 -> board[spot] = null
                BoardSpot.f4 -> board[spot] = null
                BoardSpot.g4 -> board[spot] = null
                BoardSpot.h4 -> board[spot] = null

                BoardSpot.a3 -> board[spot] = null
                BoardSpot.b3 -> board[spot] = null
                BoardSpot.c3 -> board[spot] = null
                BoardSpot.d3 -> board[spot] = null
                BoardSpot.e3 -> board[spot] = null
                BoardSpot.f3 -> board[spot] = null
                BoardSpot.g3 -> board[spot] = null
                BoardSpot.h3 -> board[spot] = null

                BoardSpot.a2 -> board[spot] = Piece.P
                BoardSpot.b2 -> board[spot] = Piece.P
                BoardSpot.c2 -> board[spot] = Piece.P
                BoardSpot.d2 -> board[spot] = Piece.P
                BoardSpot.e2 -> board[spot] = Piece.P
                BoardSpot.f2 -> board[spot] = Piece.P
                BoardSpot.g2 -> board[spot] = Piece.P
                BoardSpot.h2 -> board[spot] = Piece.P

                BoardSpot.a1 -> board[spot] = Piece.R
                BoardSpot.b1 -> board[spot] = Piece.N
                BoardSpot.c1 -> board[spot] = Piece.B
                BoardSpot.d1 -> board[spot] = Piece.Q
                BoardSpot.e1 -> board[spot] = Piece.K
                BoardSpot.f1 -> board[spot] = Piece.B
                BoardSpot.g1 -> board[spot] = Piece.N
                BoardSpot.h1 -> board[spot] = Piece.R
            }
        }
        return board
    }

    fun makeMove(game: Game, board: Board, move: Move): Boolean {
        // first check to see if there's a destination spot for this move (not a draw or castle)
        val destination = move.destinationSpot
        val piece = move.piece
        if (destination != null && piece != null) {
            // todo: handle pawn promotion
            if (move.isTaking) {
                // if this move is taking another piece make sure a piece is there and replace with piece being moved
                val pieceBeingTaken = board[destination]
                if (pieceBeingTaken == null) {
                    return false
                } else {
                    board[destination] = piece
                    return true
                }
            } else {
                if (board[destination] != null) {
                    return false
                } else {
                    board[destination] = piece
                    return true
                }
            }
        } else {
            if (move.isCastlingKingSide) {
                /* RULES OF THE KING SIDE CASTLE
                *   1. You can not castle if the king has already moved or if the rook in question has moved
                *   2. You can not castle if while in check.
                *   3. you can castle with a rook that is under attack at the time and the rook can pass through an attacked square when castling while the king can not.
                *  */

                val kingsCurrentSpot = board.find(Piece.K)
                if (kingsCurrentSpot == null) {
                    return false
                }

                // check to make sure king is at initial spot, bishop and knight have moved, and rook is still there
                if (kingsCurrentSpot == BoardSpot.e1
                    && board[BoardSpot.f1] == null
                    && board[BoardSpot.g1] == null
                    && board[BoardSpot.h1] == Piece.R) {
                    // next make sure the king isn't in check or will be moved to a spot that is being attacked.
                    // todo: need the move stack from a game to see if last move was a check.
                    if (!isKingInCheck(game)) {
                        // todo: make castle move on board
                        return true
                    } else {
                        return false
                    }
                }

            } else if (move.isCastlingQueenSide) {

            } else if (move.isOfferingDraw) {

            }
        }
        // todo: remove this
        return false
    }

    private fun isKingInCheck(game: Game): Boolean = try { game.moves.last().isCheck } catch (e: Exception) { false }

    private fun willKingBeInCheckWithThisMove(game: Game, move: Move): Boolean {
        move.destinationSpot?.let {
            /* todo: figure out this logic for determining if the piece being moved (king included) puts the king under attack.
            *   a couple quick notes on this...
            *       1. determine what piece is moving.
            *       2. if its the king.
            *           get the pieces from the other player (besides the other king).
            *           iterate through and see if their attack reaches the king.
            *           if one piece does then return false. if not return true.
            *       3. if it's not the king.
            *          determine which piece moved and determine if it's a good move.
            *          if the move is valid. get the pieces from the other player and determine if any can now put the king in check
            *          if so return false, if not return true.
            * */
        }
        return false
    }
}