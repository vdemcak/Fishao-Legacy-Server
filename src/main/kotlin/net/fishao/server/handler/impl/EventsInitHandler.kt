package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestEventsInit
import net.fishao.server.protocol.sr.ResponseEventsInit
import net.fishao.server.session.Session

class EventsInitHandler : MessageHandler<RequestEventsInit> {
    override val requestClass: Class<RequestEventsInit> = RequestEventsInit::class.java

    override suspend fun handle(session: Session, request: RequestEventsInit) {
        val response = ResponseEventsInit(
            data = "[]",
            data_coefficients = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
