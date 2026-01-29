package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestFeaturesUpdate
import net.fishao.server.protocol.sr.ResponseFeaturesUpdate
import net.fishao.server.session.Session

class FeaturesUpdateHandler : MessageHandler<RequestFeaturesUpdate> {
    override val requestClass: Class<RequestFeaturesUpdate> = RequestFeaturesUpdate::class.java

    override suspend fun handle(session: Session, request: RequestFeaturesUpdate) {
        val response = ResponseFeaturesUpdate(
            featuresJson = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
