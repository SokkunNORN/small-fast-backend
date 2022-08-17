package com.sokkun.smallfasttransfer.api.request.filter

data class TransactionFilterReq(
    val q: String?,
    val statusId: Long?,
    val senderBankId: Long?,
    val receiverBankId: Long?,
    val currencyId: Long?
)
