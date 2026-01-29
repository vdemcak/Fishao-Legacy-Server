package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestAnnouncements
import net.fishao.server.protocol.sr.ResponseAnnouncements
import net.fishao.server.session.Session

class AnnouncementsInitHandler : MessageHandler<RequestAnnouncements> {
    override val requestClass: Class<RequestAnnouncements> = RequestAnnouncements::class.java

    override suspend fun handle(session: Session, request: RequestAnnouncements) {
        val response = ResponseAnnouncements(
            data = """{"list":[]}"""
        ).applyCommon(request)

        session.send(response)
    }
}
