package com.sokkun.smallfasttransfer.api.request

import com.sokkun.smallfasttransfer.common.getOrElseThrow
import java.math.BigDecimal

data class BalanceReq(
    val balance: BigDecimal?,
    val currencyId: Long?,
    val participantUserId: Long?
) {
    init {
        getOrElseThrow("balance", balance)
        getOrElseThrow("currencyId", currencyId)
        getOrElseThrow("participantUserId", participantUserId)
    }
}