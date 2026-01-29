package net.fishao.server.session

import net.fishao.server.protocol.sr.ServerResp

/**
 * Transport-agnostic session interface.
 * Represents a client connection regardless of the underlying transport (WebSocket, TCP, etc.)
 */
interface Session {
    val id: String

    /**
     * Send a response to the client
     */
    suspend fun send(response: ServerResp)

    /**
     * Close the session
     */
    suspend fun close()
}
