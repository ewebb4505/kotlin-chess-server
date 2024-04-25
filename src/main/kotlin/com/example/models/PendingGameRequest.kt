package com.example.models

import java.time.LocalDateTime
import java.util.*

data class PendingGameRequest(
    val requested: UserId,
    val requester: UserId
) {
    val id: UUID = UUID.randomUUID()
    val timeCreated: LocalDateTime = LocalDateTime.now()
    val timeExpires: LocalDateTime
        get() = timeCreated.plusSeconds(30)
}

