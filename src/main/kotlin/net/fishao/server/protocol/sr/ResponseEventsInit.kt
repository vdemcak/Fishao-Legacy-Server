package net.fishao.server.protocol.sr

data class ResponseEventsInit(
    var data: String? = null,
    var data_coefficients: String? = null
) : ServerResp()
