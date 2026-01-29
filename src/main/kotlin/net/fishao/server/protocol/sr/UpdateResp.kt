package net.fishao.server.protocol.sr

data class UpdateResp(
    var sid: String,
    var login: String,
    var triesCount: Int = 0,
) : ServerRespWithData()