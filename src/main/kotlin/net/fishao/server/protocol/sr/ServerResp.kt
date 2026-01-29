package net.fishao.server.protocol.sr

open class ServerResp(
    var reqId: Int = 0,
    var maintenanceTime: Int = 0,
    var maintenanceIn: Int = 0,
    var needLog: Boolean = true
)