package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestQuestsInit
import net.fishao.server.protocol.sr.ResponseQuestsInit
import net.fishao.server.session.Session

class QuestsInitHandler : MessageHandler<RequestQuestsInit> {
    override val requestClass: Class<RequestQuestsInit> = RequestQuestsInit::class.java

    override suspend fun handle(session: Session, request: RequestQuestsInit) {
        val response = ResponseQuestsInit(
            questsData = "{}"
        ).applyCommon(request)

        session.send(response)
    }
}
