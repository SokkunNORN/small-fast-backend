package com.sokkun.smallfasttransfer.api.request

import com.sokkun.smallfasttransfer.common.getOrElseThrow
import java.math.BigDecimal

data class TransactionReq(
    val senderAccountId: Long?,
    val receiverAccountId: Long?,
    val amount: BigDecimal?,
    val currencyId: Long?,
    val message: String? = null
) {
    init {
        getOrElseThrow("senderAccountId", senderAccountId)
        getOrElseThrow("receiverAccountId", receiverAccountId)
        getOrElseThrow("amount", amount)
        getOrElseThrow("currencyId", currencyId)
    }
}
