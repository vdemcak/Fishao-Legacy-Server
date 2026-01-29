package net.fishao.server.protocol.cr

import java.io.Serializable

data class EnterWorld(
    var worldId: String? = null,
    var fromHost: String? = null,
    var sid: String? = null,
) : ClientRequest(), Serializable