package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.LoginReq
import net.fishao.server.protocol.sr.LoginSuccessResp
import net.fishao.server.session.Session
import java.util.*

class LoginHandler : MessageHandler<LoginReq> {
    override val requestClass: Class<LoginReq> = LoginReq::class.java

    override suspend fun handle(session: Session, request: LoginReq) {
        val response = LoginSuccessResp(
            sid = generateSessionId(),
            login = "vdemcak",
            lang = request.lang ?: "en",
            referralsDisabled = false,
            isGuest = false,
            unreadPrivateMessages = 0,
            emailActivation = 1
        ).applyCommon(request)

        session.send(response)
    }

    private fun generateSessionId(): String {
        return UUID.randomUUID().toString()
    }
}
