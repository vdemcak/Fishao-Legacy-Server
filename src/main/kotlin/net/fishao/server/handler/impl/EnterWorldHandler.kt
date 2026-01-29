package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.EnterWorld
import net.fishao.server.protocol.sr.UpdateResp
import net.fishao.server.session.Session

/**
 * EnterWorld is sent by the client right after logging in and receiving all the
 * manager initialization packets.
 */
class EnterWorldHandler : MessageHandler<EnterWorld> {
    override val requestClass: Class<EnterWorld> = EnterWorld::class.java

    override suspend fun handle(session: Session, request: EnterWorld) {
        val response = UpdateResp(
            sid = session.id,
            login = "vdemcak",
        ).apply {
            data = mapOf(
                "isModerator" to "false",
                "charView" to """{"body":{"color":1,"type":1},"hat":{"color":0,"type":-1},"hair":{"color":1,"type":1},"icon":{"color":0,"type":0},"lowerClothes":{"color":1,"type":1},"shoes":{"color":1,"type":1},"suit":{"color":0,"type":-1},"upperClothes":{"color":1,"type":1}}""",
                "hair" to "{}",
                "upper" to "{}",
                "lower" to "{}",
                "shoes" to "{}"
            )
        }.applyCommon(request)

        session.send(response)
    }
}
