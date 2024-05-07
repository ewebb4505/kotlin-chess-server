package com.example

import com.example.repos.UserRepo
import com.example.repos.UserRepoImpl
import com.example.plugins.*
import com.example.services.UserService
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
//    configureSerialization()
//    configureDatabases()
//    configureMonitoring()
    install(ContentNegotiation) {
        json()
    }

    val appModule = module {
        single<UserRepo> { UserRepoImpl() }
        single { UserService(get()) }
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    configureSockets()
    configureRouting()
}
