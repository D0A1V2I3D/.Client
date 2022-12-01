package top.davidon.ironware.ui.alts

data class Account(
    var username: String,
    var token: String,
    var refreshToken: String,
    var uuid: String,
    var type: AccountType,
    var cuid: String
) {}