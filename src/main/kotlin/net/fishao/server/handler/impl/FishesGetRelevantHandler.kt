package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestFishesGetRelevant
import net.fishao.server.protocol.sr.ResponseFishesGetRelevant
import net.fishao.server.session.Session

class FishesGetRelevantHandler : MessageHandler<RequestFishesGetRelevant> {
    override val requestClass: Class<RequestFishesGetRelevant> = RequestFishesGetRelevant::class.java

    override suspend fun handle(session: Session, request: RequestFishesGetRelevant) {
        val response = ResponseFishesGetRelevant(
            data = """{"fishesCaught":[],"fishesUnavailable":[]}"""
        ).applyCommon(request)

        session.send(response)
    }
}
