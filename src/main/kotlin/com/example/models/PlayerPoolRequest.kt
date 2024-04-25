package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayerPoolRequest(
    val userId: String,
    val status: PlayerPoolStatus,
    val requestToPlayerUserId: String? = null,
    val requestFromPlayerUserId: String? = null
)