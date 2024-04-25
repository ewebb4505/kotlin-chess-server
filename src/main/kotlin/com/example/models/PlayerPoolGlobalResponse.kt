package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class PlayerPoolGlobalResponse(val usersAndStatus: Map<UserId, PlayerPoolStatus>)