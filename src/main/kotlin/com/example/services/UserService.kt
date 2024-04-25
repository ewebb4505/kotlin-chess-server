package com.example.services

import com.example.models.UserId
import com.example.repos.UserRepo

class UserService(private val userRepository: UserRepo) {
    fun getUsers(): List<UserId> = userRepository.getUsers()
    fun createUser(): UserId = userRepository.createUser()
    fun addUser(userId: UserId) = userRepository.addUser(userId)
    fun isValidUserId(id: UserId): Boolean = userRepository.isValidUserId(id)
}