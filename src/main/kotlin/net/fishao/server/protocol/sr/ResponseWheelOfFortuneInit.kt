package net.fishao.server.protocol.sr

data class ResponseWheelOfFortuneInit(
    var rewards_json: String? = null
) : ServerResp()
