package net.fishao.server.protocol.sr

data class ResponseQuestsInit(
    var questsData: String? = null
) : ServerResp()
