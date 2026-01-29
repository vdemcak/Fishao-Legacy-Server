package net.fishao.server.protocol.sr

data class ResponseMoneyTreeInit(
    var currentLevel: Int = 0,
    var timeToNextUpgrade: Int = 0,
    var levelsData: String? = null,
    var timeToNextGenerate: Int = 0,
    var fishcoinsLeft: Int = 0,
    var fishbucksLeft: Int = 0
) : ServerResp()
