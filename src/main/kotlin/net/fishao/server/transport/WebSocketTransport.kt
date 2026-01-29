package net.fishao.server.transport

import io.ktor.websocket.*
import net.fishao.server.handler.MessageRegistry
import net.fishao.server.handler.MessageRouter
import net.fishao.server.session.WebSocketSessionAdapter

/**
 * WebSocket transport implementation.
 * This class handles WebSocket-specific frame processing.
 */
class WebSocketTransport(
    registry: MessageRegistry,
    router: MessageRouter,
) : Transport(registry, router) {
    private val headerSize: Int = 2

    /**
     * Handle a WebSocket session
     */
    suspend fun handleSession(wsSession: WebSocketSession) {
        println("[WS TRANSPORT] Client connected")

        val session = WebSocketSessionAdapter(wsSession, registry)

        for (frame in wsSession.incoming) {
            if (frame is Frame.Binary) {
                processFrame(session, frame)
            }
        }

        println("[WS TRANSPORT] Client disconnected")
    }

    private suspend fun processFrame(session: WebSocketSessionAdapter, frame: Frame.Binary) {
        val rawData = frame.readBytes()

        try {
            if (rawData.size < headerSize) {
                println("[WS TRANSPORT] Frame too small: ${rawData.size} bytes")
                return
            }

            val amfData = rawData.sliceArray(headerSize until rawData.size)
            router.processMessage(session, amfData)

        } catch (e: SecurityException) {
            println("[WS TRANSPORT] SECURITY VIOLATION: ${e.message}")
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            println("[WS TRANSPORT] Invalid AMF3 data: ${e.message}")
            e.printStackTrace()
        } catch (e: Exception) {
            println("[WS TRANSPORT] Error processing AMF3: ${e.message}")
            e.printStackTrace()
        }
    }
}
