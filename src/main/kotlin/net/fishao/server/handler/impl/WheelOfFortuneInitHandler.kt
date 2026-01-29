package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestWheelOfFortuneInit
import net.fishao.server.protocol.sr.ResponseWheelOfFortuneInit
import net.fishao.server.session.Session

class WheelOfFortuneInitHandler : MessageHandler<RequestWheelOfFortuneInit> {
    override val requestClass: Class<RequestWheelOfFortuneInit> = RequestWheelOfFortuneInit::class.java

    override suspend fun handle(session: Session, request: RequestWheelOfFortuneInit) {
        val response = ResponseWheelOfFortuneInit(
            rewards_json = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
