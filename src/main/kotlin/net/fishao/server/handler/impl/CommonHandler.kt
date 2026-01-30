package net.fishao.server.handler.impl

import net.fishao.server.protocol.cr.ClientRequest
import net.fishao.server.protocol.sr.ServerResp

/**
 * ApplyCommon applies the common fields from the request to the response in order to properly link them.
 *
 * TODO: Query DB/Cache for maintenance info
 */
internal fun <T : ServerResp> T.applyCommon(request: ClientRequest): T {
    reqId = request.reqId
    maintenanceTime = 0
    maintenanceIn = 0
    needLog = false
    return this
}
