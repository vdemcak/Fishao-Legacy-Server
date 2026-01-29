package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.RequestFurnitureFactoryGetInfo
import net.fishao.server.protocol.sr.ResponseFurnitureFactoryGetInfo
import net.fishao.server.session.Session

class FurnitureFactoryGetInfoHandler : MessageHandler<RequestFurnitureFactoryGetInfo> {
    override val requestClass: Class<RequestFurnitureFactoryGetInfo> = RequestFurnitureFactoryGetInfo::class.java

    override suspend fun handle(session: Session, request: RequestFurnitureFactoryGetInfo) {
        val response = ResponseFurnitureFactoryGetInfo(
            skip_time_price_fishcoins = 0,
            reward_time_left = 0
        ).applyCommon(request)

        session.send(response)
    }
}
