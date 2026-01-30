package net.fishao.server.protocol.cr

import java.io.Serializable

data class EnterLocation(
    var locationId: Int,
    var lastLocationId: Int,
    var lastVirtualLocationId: Int
) : ClientRequest(), Serializable