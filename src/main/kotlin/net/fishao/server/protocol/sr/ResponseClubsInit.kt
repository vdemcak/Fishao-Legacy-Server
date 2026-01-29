package net.fishao.server.protocol.sr

data class ResponseClubsInit(
    var club_info: String? = null,
    var levels_data: String? = null,
    var create_cost: Int = 0
) : ServerResp()
