package com.example

import com.example.Utils.MoveUtils
import com.example.models.BoardSpot
import com.example.models.Move
import com.example.models.Piece
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MoveUtilsTest {

    @Test
    fun testMoveUtils_invalidMoveSyntax() {

    }

    @Test
    fun testMoveUtils_parseKingSideCastle() {
        val kingSideCastleString = "4_0-0"
        val correctMove = Move(kingSideCastleString).also {
            it.isCastlingQueenSide = true
            it.number = 4
        }
        val move = MoveUtils.parseMove(kingSideCastleString)
        assertEquals(correctMove, move, "Move should have prop isCastlingKingSide as true and number 4")
    }

    @Test
    fun testMoveUtils_parseQueenSideCastle() {
        val queenSideCastleString = "8_0-0-0"
        val correctMove = Move(queenSideCastleString).also {
            it.isCastlingQueenSide = true
            it.number = 8
        }
        val move = MoveUtils.parseMove(queenSideCastleString)
        assertEquals(correctMove, move, "Move should have prop isCastlingQueenSide as true and number 8")
    }

    @Test
    fun testMoveUtils_parseDrawOffer() {
        val drawOffer = "16_(=)"
        val correctMove = Move(drawOffer).also {
            it.isOfferingDraw = true
            it.number = 16
        }
        val move = MoveUtils.parseMove(drawOffer)
        assertEquals(correctMove, move, "Move should have prop isOfferingDraw as true and number 16")
    }

    @Test
    fun testMoveUtils_FischervsByrne1956() {
        val actualMove1 = MoveUtils.parseMove(FischerVsBryne.move1)
        val expectedMove1 = Move(FischerVsBryne.move1).also {
            it.number = 1
            it.piece = Piece.N
            it.destinationSpot = BoardSpot.f3
        }
        assertEquals(expectedMove1, actualMove1, "should be valid")

        val actualMove2 = MoveUtils.parseMove(FischerVsBryne.move2)
        val expectedMove2 = Move(FischerVsBryne.move2).also {
            it.number = 2
            it.piece = Piece.N
            it.destinationSpot = BoardSpot.f6
        }
        assertEquals(expectedMove2, actualMove2, "should be valid")

        val actualMove3 = MoveUtils.parseMove(FischerVsBryne.move3)
        val expectedMove3 = Move(FischerVsBryne.move3).also {
            it.number = 3
            it.piece = Piece.P
            it.destinationSpot = BoardSpot.c4
        }
        assertEquals(expectedMove3, actualMove3, "should be valid")

        //4_Pg6
        val actualMove4 = MoveUtils.parseMove(FischerVsBryne.move4)
        val expectedMove4 = Move(FischerVsBryne.move4).also {
            it.number = 4
            it.piece = Piece.P
            it.destinationSpot = BoardSpot.g6
        }
        assertEquals(expectedMove4, actualMove4, "should be valid")

        //5_Nc3
        val actualMove5 = MoveUtils.parseMove(FischerVsBryne.move5)
        val expectedMove5 = Move(FischerVsBryne.move5).also {
            it.number = 5
            it.piece = Piece.N
            it.destinationSpot = BoardSpot.c3
        }
        assertEquals(expectedMove5, actualMove5, "should be valid")

        //6_Bg7
        val actualMove6 = MoveUtils.parseMove(FischerVsBryne.move6)
        val expectedMove6 = Move(FischerVsBryne.move6).also {
            it.number = 6
            it.piece = Piece.B
            it.destinationSpot = BoardSpot.g7
        }
        assertEquals(expectedMove6, actualMove6, "should be valid")

        //7_Pd4
        val actualMove7 = MoveUtils.parseMove(FischerVsBryne.move7)
        val expectedMove7 = Move(FischerVsBryne.move7).also {
            it.number = 7
            it.piece = Piece.P
            it.destinationSpot = BoardSpot.d4
        }
        assertEquals(expectedMove7, actualMove7, "should be valid")

        //8_0-0
        val actualMove8 = MoveUtils.parseMove(FischerVsBryne.move8)
        val expectedMove8 = Move(FischerVsBryne.move8).also {
            it.number = 8
            it.isCastlingKingSide = true
        }
        assertEquals(expectedMove8, actualMove8, "should be valid")

        //9_Bf4
        val actualMove9 = MoveUtils.parseMove(FischerVsBryne.move9)
        val expectedMove9 = Move(FischerVsBryne.move9).also {
            it.number = 9
            it.piece = Piece.B
            it.destinationSpot = BoardSpot.f4
        }
        assertEquals(expectedMove9, actualMove9, "should be valid")

        //10_Pd5
        val actualMove10 = MoveUtils.parseMove(FischerVsBryne.move10)
        val expectedMove10 = Move(FischerVsBryne.move10).also {
            it.number = 10
            it.piece = Piece.P
            it.destinationSpot = BoardSpot.d5
        }
        assertEquals(expectedMove10, actualMove10, "should be valid")

        //TODO: test next 10
    }
}