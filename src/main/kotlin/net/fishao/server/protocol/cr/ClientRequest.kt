package net.fishao.server.protocol.cr

import java.io.Serializable

open class ClientRequest(
    var reqId: Int = -1
) : Serializable