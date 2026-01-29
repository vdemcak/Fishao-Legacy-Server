package net.fishao.server.protocol.sr

data class LoginSuccessResp(
    var sid: String = "",
    var login: String = "",
    var lang: String = "",
    var referralsDisabled: Boolean = false,
    var isGuest: Boolean = false,
    var unreadPrivateMessages: Int = 0,
    var emailActivation: Int = 0
) : ServerResp() {
    @Override
    override fun toString(): String {
        return "LoginSuccessResp(reqId=$reqId, sid=$sid, login=$login, lang=$lang, referralsDisabled=$referralsDisabled, isGuest=$isGuest, unreadPrivateMessages=$unreadPrivateMessages, emailActivation=$emailActivation, maintenanceTime=$maintenanceTime, maintenanceIn=$maintenanceIn, needLog=$needLog)"
    }
}