package net.fishao.server.protocol.sr

data class ResponseFeaturesTemporaryInit(
    var features_temporary: String? = null
) : ServerResp()
