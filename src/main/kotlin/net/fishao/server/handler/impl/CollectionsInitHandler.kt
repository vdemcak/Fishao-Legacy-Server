package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestCollectionsInit
import net.fishao.server.protocol.sr.ResponseCollectionsInit
import net.fishao.server.session.Session

class CollectionsInitHandler : MessageHandler<RequestCollectionsInit> {
    override val requestClass: Class<RequestCollectionsInit> = RequestCollectionsInit::class.java

    override suspend fun handle(session: Session, request: RequestCollectionsInit) {
        val response = ResponseCollectionsInit(
            list = "[]"
        ).applyCommon(request)

        session.send(response)
    }
}
