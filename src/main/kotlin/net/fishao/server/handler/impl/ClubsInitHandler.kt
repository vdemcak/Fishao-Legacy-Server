package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestClubsInit
import net.fishao.server.protocol.sr.ResponseClubsInit
import net.fishao.server.session.Session

class ClubsInitHandler : MessageHandler<RequestClubsInit> {
    override val requestClass: Class<RequestClubsInit> = RequestClubsInit::class.java

    override suspend fun handle(session: Session, request: RequestClubsInit) {
        val response = ResponseClubsInit(
            club_info = null,
            levels_data = "[]",
            create_cost = 0
        ).applyCommon(request)

        session.send(response)
    }
}
