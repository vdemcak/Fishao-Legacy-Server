package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestHolidaysInit
import net.fishao.server.protocol.sr.ResponseHolidaysInit
import net.fishao.server.session.Session

class HolidaysInitHandler : MessageHandler<RequestHolidaysInit> {
    override val requestClass: Class<RequestHolidaysInit> = RequestHolidaysInit::class.java

    override suspend fun handle(session: Session, request: RequestHolidaysInit) {
        val response = ResponseHolidaysInit(
            data = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
