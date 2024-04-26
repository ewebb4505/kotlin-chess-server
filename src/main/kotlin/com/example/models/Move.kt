package com.example.models

data class Move(val notation: String) {
    var isTaking: Boolean = false
    var number: Int = 0
    var piece: Piece? = null
    var isCastlingKingSide: Boolean = false
    var isCastlingQueenSide: Boolean = false
    var isOfferingDraw: Boolean = false
    var isPawnPromotion: Boolean = false
}