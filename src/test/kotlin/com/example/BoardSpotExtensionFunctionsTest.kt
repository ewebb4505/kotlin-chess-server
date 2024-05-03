package com.example

import com.example.models.BoardSpot
import com.example.models.getAdjacent
import org.junit.Test
import kotlin.test.assertEquals

internal class BoardSpotExtensionFunctionsTest {

    @Test
    fun testAdjacentSpots() {
        // testing spots on the corners first (should have a total of 3 adjacent spots each)
        val boardSpotA1 = BoardSpot.a1
        val boardSpotA8 = BoardSpot.a8
        val boardSpotH1 = BoardSpot.h1
        val boardSpotH8 = BoardSpot.h8

        val adjacentSpotsToA1 = setOf(BoardSpot.a2, BoardSpot.b1, BoardSpot.b2)
        val adjacentSpotsToA8 = setOf(BoardSpot.a7, BoardSpot.b8, BoardSpot.b7)
        val adjacentSpotsToH1 = setOf(BoardSpot.h2, BoardSpot.g1, BoardSpot.g2)
        val adjacentSpotsToH8 = setOf(BoardSpot.h7, BoardSpot.g8, BoardSpot.g7)

        assertEquals(boardSpotA1.getAdjacent(), adjacentSpotsToA1, "should be set of a2, b1, b2")
        assertEquals(boardSpotA8.getAdjacent(), adjacentSpotsToA8, "should be set of a7, b8, b7")
        assertEquals(boardSpotH1.getAdjacent(), adjacentSpotsToH1, "should be set of h2, g1, g2")
        assertEquals(boardSpotH8.getAdjacent(), adjacentSpotsToH8, "should be set of h7, g8, g7")

        // testing spots on the sides but not in the corner (should have a total of 5 adjacent spots each)
        val boardSpotA4 = BoardSpot.a4
        val boardSpotD1 = BoardSpot.d1
        val boardSpotH5 = BoardSpot.h5
        val boardSpotD8 = BoardSpot.d8

        val adjacentSpotsToA4 = setOf(BoardSpot.a3, BoardSpot.b3, BoardSpot.b4, BoardSpot.b5, BoardSpot.a5)
        val adjacentSpotsToD1 = setOf(BoardSpot.c1, BoardSpot.e1, BoardSpot.c2, BoardSpot.d2, BoardSpot.e2)
        val adjacentSpotsToH5 = setOf(BoardSpot.h4, BoardSpot.h6, BoardSpot.g4, BoardSpot.g5, BoardSpot.g6)
        val adjacentSpotsToD8 = setOf(BoardSpot.e8, BoardSpot.c8, BoardSpot.e7, BoardSpot.d7, BoardSpot.c7)

        assertEquals(boardSpotA4.getAdjacent(), adjacentSpotsToA4, "sets do not match")
        assertEquals(boardSpotD1.getAdjacent(), adjacentSpotsToD1, "sets do not match")
        assertEquals(boardSpotH5.getAdjacent(), adjacentSpotsToH5, "sets do not match")
        assertEquals(boardSpotD8.getAdjacent(), adjacentSpotsToD8, "sets do not match")

        // testing spots on the inside (should have a total of 8 adjacent spots)
        val boardSpotD4 = BoardSpot.d4
        val boardSpotF6 = BoardSpot.f6

        val adjacentSpotsToD4 = setOf(
            BoardSpot.c4,
            BoardSpot.e4,
            BoardSpot.c5,
            BoardSpot.d5,
            BoardSpot.e5,
            BoardSpot.c3,
            BoardSpot.d3,
            BoardSpot.e3,
        )

        val adjacentSpotsToF6 = setOf(
            BoardSpot.e6,
            BoardSpot.g6,
            BoardSpot.e7,
            BoardSpot.f7,
            BoardSpot.g7,
            BoardSpot.e5,
            BoardSpot.f5,
            BoardSpot.g5,
        )

        assertEquals(boardSpotD4.getAdjacent(), adjacentSpotsToD4, "sets do not match")
        assertEquals(boardSpotF6.getAdjacent(), adjacentSpotsToF6, "sets do not match")
    }
}