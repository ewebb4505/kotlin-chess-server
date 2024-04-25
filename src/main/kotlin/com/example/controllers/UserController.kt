package com.example.controllers

import com.example.services.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoute() {
    val service by inject<UserService>()
    route("user") {
        get {
            call.respond(service.getUsers())
        }
        post {
            val user = service.createUser()
            service.addUser(user)
            call.respond(user)
        }
    }
}