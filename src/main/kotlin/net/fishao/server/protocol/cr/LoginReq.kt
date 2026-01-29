package net.fishao.server.protocol.cr

import java.io.Serializable

data class LoginReq(
    var login: String? = null,
    var pass: String? = null,
    var pid: String? = null,
    var ptype: String? = null,
    var lang: String? = null
) : ClientRequest(), Serializable {
    override fun toString(): String {
        return "LoginReq(reqId=$reqId, login=$login, pass=$pass, pid=$pid, ptype=$ptype, lang=$lang)"
    }
}