package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestFeaturesTemporaryInit
import net.fishao.server.protocol.sr.ResponseFeaturesTemporaryInit
import net.fishao.server.session.Session

class FeaturesTemporaryInitHandler : MessageHandler<RequestFeaturesTemporaryInit> {
    override val requestClass: Class<RequestFeaturesTemporaryInit> = RequestFeaturesTemporaryInit::class.java

    override suspend fun handle(session: Session, request: RequestFeaturesTemporaryInit) {
        val response = ResponseFeaturesTemporaryInit(
            features_temporary = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
