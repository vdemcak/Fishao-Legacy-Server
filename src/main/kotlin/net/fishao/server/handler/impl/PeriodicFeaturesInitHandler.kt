package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestPeriodicFeaturesInit
import net.fishao.server.protocol.sr.ResponsePeriodicFeaturesInit
import net.fishao.server.session.Session

class PeriodicFeaturesInitHandler : MessageHandler<RequestPeriodicFeaturesInit> {
    override val requestClass: Class<RequestPeriodicFeaturesInit> = RequestPeriodicFeaturesInit::class.java

    override suspend fun handle(session: Session, request: RequestPeriodicFeaturesInit) {
        val response = ResponsePeriodicFeaturesInit(
            data = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
