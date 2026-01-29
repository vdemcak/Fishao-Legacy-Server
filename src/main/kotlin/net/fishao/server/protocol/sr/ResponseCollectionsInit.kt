package net.fishao.server.protocol.sr

data class ResponseCollectionsInit(
    var list: String? = null
) : ServerResp()
