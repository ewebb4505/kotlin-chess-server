package com.example.models

import com.example.exceptions.IllegalSpotException
import com.example.models.Piece.*

/**
 * A chess board.
 *
 * enum class of different spots on a board. first letter is the file, number on the end is the row.
 */
enum class BoardSpot {
    a8, b8, c8, d8, e8, f8, g8, h8,
    a7, b7, c7, d7, e7, f7, g7, h7,
    a6, b6, c6, d6, e6, f6, g6, h6,
    a5, b5, c5, d5, e5, f5, g5, h5,
    a4, b4, c4, d4, e4, f4, g4, h4,
    a3, b3, c3, d3, e3, f3, g3, h3,
    a2, b2, c2, d2, e2, f2, g2, h2,
    a1, b1, c1, d1, e1, f1, g1, h1;


    companion object {
        /**
         * constructs an optional board spot based on row and file.
         *
         * @property num row number of desired board spot
         * @property file file of desired board spot
         * @returns null if num and file don't create a proper board spot.
         */
        fun construct(num: Int, file: String): BoardSpot? = try { BoardSpot.valueOf(file + num.toString()) } catch (e: Exception) { null }

        val files: Array<String> = arrayOf("a","b","c","d","e","f","g","h")
    }
}

/**
 * gets row number
 *
 * @returns row number in Int format
 */
fun BoardSpot.row(): Int = this.toString().last().toString().toInt()

/**
 * gets file letter
 *
 * @returns file letter in form of a String
 */
fun BoardSpot.file(): String = this.toString().first().toString()

/**
 * finds a board spot based on moving up, down, left, and right from this spot.
 *
 * @property up number of spots up from current spot
 * @property down number of spots down from current spot
 * @property left number of spots left from current spot
 * @property right number of spots right from current spot
 * @returns board spot if number of moves up, down, left, and right are still on the board. null if not
 */
fun BoardSpot.next(up: Int = 0, down: Int = 0, left: Int = 0, right: Int = 0): BoardSpot? {
    if (up > 8 || down > 8 || left > 8 || right > 8) return null

    val num = this.row()
    val file = this.file()
    val currentFileIndex = BoardSpot.files.indexOf(file)

    val isStillInBounds = (num + up <= 8)
            && (num - down >= 1)
            && ((currentFileIndex+1) - left >= 1)
            &&  ((currentFileIndex+1) + right <= 8)

    if (!isStillInBounds) return null

    val newNum: Int = num + (up - down)
    val newFileIndex: Int = currentFileIndex + (right - left)
    val newFile = BoardSpot.files[newFileIndex]

    return BoardSpot.construct(newNum, newFile)
}

/**
 * creates a set of board spots that are adjacent to this board spot
 *
 * @returns Set<BoardSpot>
 */
fun BoardSpot.getAdjacent(): Set<BoardSpot> = setOf(
        this.next(up = 1),
        this.next(down = 1),
        this.next(right = 1),
        this.next(left = 1),
        this.next(up = 1, right = 1),
        this.next(up = 1, left = 1),
        this.next(down = 1, right = 1),
        this.next(down = 1, left = 1)
    ).filterNotNull().toSet()