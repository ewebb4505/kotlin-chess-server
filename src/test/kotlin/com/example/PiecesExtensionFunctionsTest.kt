package com.example

import com.example.Utils.GameUtils
import com.example.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PiecesExtensionFunctionsTest {

    @Test
    fun testSquaresAttackedBy() {
        val whitePieces: Pieces = GameUtils.setPiecesForWhite()
        val originalKnightSpots = whitePieces[Piece.N]
        assertEquals(originalKnightSpots, mutableListOf(BoardSpot.b1, BoardSpot.g1))

        // testing the knight
        val placesKnightOnG1CanPossiblyMove = setOf(BoardSpot.f3, BoardSpot.h3, BoardSpot.e2)
        val squaresWhereKnightCanMove = whitePieces.squaresAttackedBy(Piece.N, BoardSpot.g1)
        assertEquals(placesKnightOnG1CanPossiblyMove, squaresWhereKnightCanMove)

        // testing the queen
        val placesQueenCanCover = BoardSpot.entries.toSet().minus(BoardSpot.d1)
        val squaresWhereQueenCanMove = whitePieces.squaresAttackedBy(Piece.Q, BoardSpot.d1)
        assertEquals(placesQueenCanCover, squaresWhereQueenCanMove)

        // testing the bishop
        val placesBishopCanCover = setOf(
            BoardSpot.construct(2, "d"),
            BoardSpot.construct(3, "e"),
            BoardSpot.construct(4, "f"),
            BoardSpot.construct(5, "g"),
            BoardSpot.construct(6, "h"),
            BoardSpot.construct(2, "b"),
            BoardSpot.construct(3, "a"),
        ).filterNotNull().toSet()
        val squaresWhereBishopCanCover = whitePieces.squaresAttackedBy(Piece.B, BoardSpot.c1)
        assertEquals(placesBishopCanCover, squaresWhereBishopCanCover)

        // testing the rook
        val placesRookCanCover = setOf(
            BoardSpot.construct(2, "h"),
            BoardSpot.construct(3, "h"),
            BoardSpot.construct(4, "h"),
            BoardSpot.construct(5, "h"),
            BoardSpot.construct(6, "h"),
            BoardSpot.construct(7, "h"),
            BoardSpot.construct(8, "h"),
            BoardSpot.construct(1, "g"),
            BoardSpot.construct(1, "f"),
            BoardSpot.construct(1, "e"),
            BoardSpot.construct(1, "d"),
            BoardSpot.construct(1, "c"),
            BoardSpot.construct(1, "b"),
            BoardSpot.construct(1, "a"),
        )
        val squaresWhereRookCanCover = whitePieces.squaresAttackedBy(Piece.R, BoardSpot.h1)
        assertEquals(placesRookCanCover, squaresWhereRookCanCover)

        //testing a pawn
        val placesThisPawnCanCover = setOf(
            BoardSpot.construct(3, "b"),
            BoardSpot.construct(3, "d"),
        )
        val squaresWhereThisPawnCanCover = whitePieces.squaresAttackedBy(Piece.P, BoardSpot.c2)
        assertEquals(placesThisPawnCanCover, squaresWhereThisPawnCanCover)
    }
}