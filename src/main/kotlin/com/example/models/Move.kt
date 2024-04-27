package com.example.models

/**
 * A chess move.
 *
 * This data class hold the properties of a chess move.
 *
 * @property isTaking Determines if the move is taking another piece
 * @property number Move number for a specific game
 * @property piece Piece being moved on the board
 * @property destinationSpot Where the Piece is being moved to (some moves don't have a destination spot)
 * @property isCastlingKingSide Determines if the move is a king side castle
 * @property isCastlingQueenSide Determines if the move is a queen side castle
 * @property isOfferingDraw Determines if the move is a user offering a draw
 * @property isPawnPromotion Determines if the move is a pawn promotion
 * @property promotionPiece Piece the user want to promote from a pawn
 * @property isCheck Determines if the move puts the other user in check
 * @property isCheckmate Determines if the move puts the other user in checkmate
 * @property isEnPassant Determines if the move was an en passant capture
 * @constructor Creates Move only with notation given
 */
data class Move(val notation: String) {
    var isTaking: Boolean = false
    var number: Int = 0
    var piece: Piece? = null
    var destinationSpot: BoardSpot? = null
    var isCastlingKingSide: Boolean = false
    var isCastlingQueenSide: Boolean = false
    var isOfferingDraw: Boolean = false
    var isPawnPromotion: Boolean = false
    var promotionPiece: Piece? = null
    var isCheck: Boolean = false
    var isCheckmate: Boolean = false
    var isEnPassant: Boolean = false
}