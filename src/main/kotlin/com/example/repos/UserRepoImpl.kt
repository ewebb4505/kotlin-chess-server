package com.example.repos

import com.example.models.UserId

class UserRepoImpl: UserRepo {
    private val users = mutableListOf<UserId>()

    override fun getUsers(): List<UserId> {
        return users
    }

    override fun addUser(userId: UserId) {
        users.add(userId)
    }

    override fun createUser(): UserId {
        return UserId((0..Long.MAX_VALUE).random().toString())
    }

    override fun isValidUserId(id: UserId): Boolean {
        return users.contains(id)
    }
}