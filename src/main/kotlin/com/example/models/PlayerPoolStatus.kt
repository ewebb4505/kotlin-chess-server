package com.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PlayerPoolStatus {
    @SerialName("ready_to_play")
    READY_TO_PLAY,

    @SerialName("requesting_to_play")
    REQUESTING_TO_PLAY,

    @SerialName("requested_to_play")
    REQUESTED_TO_PLAY
}