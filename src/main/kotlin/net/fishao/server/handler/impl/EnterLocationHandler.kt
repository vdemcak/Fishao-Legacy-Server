package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.EnterWorld
import net.fishao.server.protocol.sr.EnterLocationSuccessResp
import net.fishao.server.protocol.sr.UpdateResp
import net.fishao.server.session.Session

/**
 * EnterLocation is sent by the client when the player clicks on a location
 * in the world map. The server responds with EnterLocationSuccessResp or
 * TODO: EnterLocationErrorResp
 */
class EnterLocationHandler : MessageHandler<EnterWorld> {
    override val requestClass: Class<EnterWorld> = EnterWorld::class.java

    override suspend fun handle(session: Session, request: EnterWorld) {
        val response = EnterLocationSuccessResp(
        ).applyCommon(request)

        session.send(response)
    }
}
