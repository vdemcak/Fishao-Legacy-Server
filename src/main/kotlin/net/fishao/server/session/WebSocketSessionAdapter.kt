package net.fishao.server.session

import io.ktor.websocket.*
import net.fishao.server.amf3.AMF3Serializer
import net.fishao.server.handler.MessageRegistry
import net.fishao.server.protocol.sr.ServerResp
import java.nio.ByteBuffer
import java.util.*

class WebSocketSessionAdapter(
    private val wsSession: WebSocketSession,
    private val messageRegistry: MessageRegistry
) : Session {

    override val id: String = UUID.randomUUID().toString()

    override suspend fun send(response: ServerResp) {
        val serializer = AMF3Serializer()

        val alias = messageRegistry.getResponseAlias(response::class)
        if (alias != null) {
            serializer.registerAlias(response::class, alias)
        }

        serializer.writeObject(response)
        val amfBytes = serializer.toByteArray()

        val fullMessage = ByteBuffer.allocate(4 + amfBytes.size)
        fullMessage.putInt(amfBytes.size)
        fullMessage.put(amfBytes)

        wsSession.send(Frame.Binary(true, fullMessage.array()))
    }

    override suspend fun close() {
        wsSession.close()
    }
}