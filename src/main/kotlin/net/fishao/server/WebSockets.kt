package net.fishao.server

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import net.fishao.server.handler.HandlerConfig
import net.fishao.server.handler.MessageRouter
import net.fishao.server.transport.WebSocketTransport

fun Application.configureWebSockets() {
    val registry = HandlerConfig.createRegistry()
    val router = MessageRouter(registry)
    val wsTransport = WebSocketTransport(registry, router)

    install(WebSockets) {
        maxFrameSize = 1024 * 1024 // 1 MB
    }

    routing {
        webSocket("/ws") {
            wsTransport.handleSession(this)
        }
    }
}
