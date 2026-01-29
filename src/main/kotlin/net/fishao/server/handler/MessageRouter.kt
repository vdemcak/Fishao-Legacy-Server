package net.fishao.server.handler

import net.fishao.server.amf3.AMF3Deserializer
import net.fishao.server.protocol.cr.ClientRequest
import net.fishao.server.session.Session
import java.io.ByteArrayInputStream

/**
 * Routes incoming messages to the appropriate handler while providing
 * transport-agnostic message processing.
 */
class MessageRouter(private val registry: MessageRegistry) {

    /**
     * Process raw AMF3 data and routes the message to its handler
     * @param session The client session
     * @param amfData The AMF3 encoded message data (without transport headers)
     */
    suspend fun processMessage(session: Session, amfData: ByteArray) {
        val deserializer = AMF3Deserializer(ByteArrayInputStream(amfData))
        registry.configureDeserializer(deserializer)

        val obj = deserializer.readObject()

        if (obj == null) {
            println("Received null object")
            return
        }

        routeMessage(session, obj)
    }

    /**
     * Routes the message to its registered handler
     */
    private suspend fun routeMessage(session: Session, message: Any) {
        if (message !is ClientRequest) {
            return
        }

        val handler = registry.getHandler(message.javaClass)
        println("Routing message to handler: ${handler?.javaClass?.simpleName ?: "None"}")

        if (handler != null) {
            handler.handle(session, message)
        } else {
            println("No handler registered")
        }
    }
}
