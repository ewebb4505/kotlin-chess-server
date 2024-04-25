package com.example.repos

import com.example.models.UserId

interface UserRepo {
    fun getUsers(): List<UserId>
    fun addUser(userId: UserId)
    fun createUser(): UserId
    fun isValidUserId(id: UserId): Boolean
}