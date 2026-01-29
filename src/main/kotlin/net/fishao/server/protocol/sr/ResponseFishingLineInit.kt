package net.fishao.server.protocol.sr

data class ResponseFishingLineInit(
    var currentLevel: Int = 0,
    var timeToNextUpgrade: Int = 0,
    var levelsData: String? = null,
    var color: String? = null
) : ServerResp()
