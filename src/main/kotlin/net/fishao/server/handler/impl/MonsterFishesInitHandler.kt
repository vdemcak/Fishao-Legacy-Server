package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestMonsterFishesInit
import net.fishao.server.protocol.sr.ResponseMonsterFishesInit
import net.fishao.server.session.Session

class MonsterFishesInitHandler : MessageHandler<RequestMonsterFishesInit> {
    override val requestClass: Class<RequestMonsterFishesInit> = RequestMonsterFishesInit::class.java

    override suspend fun handle(session: Session, request: RequestMonsterFishesInit) {
        val response = ResponseMonsterFishesInit(
            data = """{"info":"[]","progress":"[]"}"""
        ).applyCommon(request)

        session.send(response)
    }
}
