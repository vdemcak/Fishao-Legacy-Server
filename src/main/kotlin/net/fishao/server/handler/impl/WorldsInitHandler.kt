package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestWorldsInit
import net.fishao.server.protocol.sr.ResponseWorldsInit
import net.fishao.server.session.Session

class WorldsInitHandler : MessageHandler<RequestWorldsInit> {
    override val requestClass: Class<RequestWorldsInit> = RequestWorldsInit::class.java

    override suspend fun handle(session: Session, request: RequestWorldsInit) {
        val response = ResponseWorldsInit(
            list = mapOf(
                "0" to """{ "worldId": "world1", "address": "10.0.0.11", "port": 9777, "worldName": "Dummy World 1", "count": 0, "maxPlayers": 100, "enabled": true, "worldKey": "world1", "sortName": "" }""",
            )
        ).applyCommon(request)

        session.send(response)
    }
}
