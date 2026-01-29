package net.fishao.server.protocol.sr

data class ResponseFeaturesUpdate(
    var featuresJson: String? = null
) : ServerResp()
