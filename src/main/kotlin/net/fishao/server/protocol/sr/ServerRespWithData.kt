package net.fishao.server.protocol.sr

open class ServerRespWithData (
    var data: Map<String, Any> = emptyMap()
) : ServerResp()