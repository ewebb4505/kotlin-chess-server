package com.example

class UserRepo : UserRepos {
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

interface UserRepos {
    fun getUsers(): List<UserId>
    fun addUser(userId: UserId)
    fun createUser(): UserId
    fun isValidUserId(id: UserId): Boolean
}

class UserService(private val userRepository: UserRepos) {
    fun getUsers(): List<UserId> = userRepository.getUsers()
    fun createUser(): UserId = userRepository.createUser()
    fun addUser(userId: UserId) = userRepository.addUser(userId)
    fun isValidUserId(id: UserId): Boolean = userRepository.isValidUserId(id)
}