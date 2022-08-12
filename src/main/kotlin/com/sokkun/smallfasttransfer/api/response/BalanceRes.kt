package com.sokkun.smallfasttransfer.api.response

import com.sokkun.smallfasttransfer.domain.model.CurrencyType
import java.math.BigDecimal

data class BalanceRes(
    val id: Long,
    val balance: BigDecimal,
    val currency: CurrencyType,
    val user: ParticipantUserShortRes,
    val participant: ParticipantShortRes
)
