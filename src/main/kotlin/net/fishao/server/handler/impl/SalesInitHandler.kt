package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestSalesInit
import net.fishao.server.protocol.sr.ResponseSalesInit
import net.fishao.server.session.Session

class SalesInitHandler : MessageHandler<RequestSalesInit> {
    override val requestClass: Class<RequestSalesInit> = RequestSalesInit::class.java

    override suspend fun handle(session: Session, request: RequestSalesInit) {
        val response = ResponseSalesInit(
            data = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
