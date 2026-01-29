package net.fishao.server.protocol.sr

data class ResponseWorldsInit(
    var list: Map<String, String> = emptyMap()
) : ServerResp()
