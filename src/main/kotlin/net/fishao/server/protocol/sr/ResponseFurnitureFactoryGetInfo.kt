package net.fishao.server.protocol.sr

data class ResponseFurnitureFactoryGetInfo(
    var skip_time_price_fishcoins: Int = 0,
    var reward_time_left: Int = 0
) : ServerResp()
