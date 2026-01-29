package net.fishao.server.handler.impl

import net.fishao.server.handler.MessageHandler
import net.fishao.server.protocol.cr.GetWorldMapDataReq
import net.fishao.server.protocol.sr.WorldMapDataResp
import net.fishao.server.session.Session

/**
 * World Map Data Handler returns the list of users unlocked locations and their player counts.
 */
class WorldMapDataHandler : MessageHandler<GetWorldMapDataReq> {
    override val requestClass: Class<GetWorldMapDataReq> = GetWorldMapDataReq::class.java

    override suspend fun handle(session: Session, request: GetWorldMapDataReq) {
        val locationIds = 1..12

        val userLocations = buildString {
            append("{")
            append(
                locationIds.joinToString(",") { id ->
                """
                    "$id": {
                        "unlocked": true,
                        "players": 100
                    }
                """.trimIndent()
                }
            )
            append("}")
        }

        val response = WorldMapDataResp(
            userLocations = userLocations
        ).applyCommon(request)

        session.send(response)
    }
}
