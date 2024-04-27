package com.example.models

/**
 * A chess piece.
 *
 * enum class of different chess pieces.
 *
 * @property K King
 * @property Q Queen
 * @property R Rook
 * @property B Bishop
 * @property N Knight
 * @property P Pawn
 */
enum class Piece {
    K, Q, R, B, N, P;

    override fun toString(): String {
        return when(this) {
            K -> "King"
            Q -> "Queen"
            R -> "Rook"
            B -> "Bishop"
            N -> "Knight"
            P -> "Pawn"
        }
    }
}