package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestTutorialInit
import net.fishao.server.protocol.sr.ResponseTutorialInit
import net.fishao.server.session.Session

class TutorialInitHandler : MessageHandler<RequestTutorialInit> {
    override val requestClass: Class<RequestTutorialInit> = RequestTutorialInit::class.java

    override suspend fun handle(session: Session, request: RequestTutorialInit) {
        val response = ResponseTutorialInit(
            list = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
