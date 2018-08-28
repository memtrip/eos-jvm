package com.memtrip.eos.http.rpc.model.account.response

data class AccountPermission(
    val perm_name: String,
    val parent: String,
    val required_auth: AccountRequiredAuth)