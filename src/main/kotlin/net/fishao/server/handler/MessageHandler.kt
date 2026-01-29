package net.fishao.server.handler

import net.fishao.server.protocol.cr.ClientRequest
import net.fishao.server.session.Session

/**
 * Base interface for message handlers.
 * Each handler processes a specific type of client request.
 */
interface MessageHandler<T : ClientRequest> {
    /**
     * The request class this handler processes
     */
    val requestClass: Class<T>

    /**
     * Handle the incoming request
     * @param session The client session
     * @param request The deserialized request
     */
    suspend fun handle(session: Session, request: T)
}
