package com.example.plugins

import com.example.UserRepo
import com.example.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing { userRoute() }
}

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

