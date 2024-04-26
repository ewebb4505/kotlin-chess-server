package com.example.Utils

import com.example.models.Board
import com.example.models.BoardSpot
import com.example.models.Move
import com.example.models.Piece

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

    fun isValidMove(board: Board, move: Move) {
        // todo
    }
}