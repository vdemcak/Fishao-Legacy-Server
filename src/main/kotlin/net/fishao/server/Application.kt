package net.fishao.server

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureWebSockets()
    configureRouting()
    configureCors()
}
