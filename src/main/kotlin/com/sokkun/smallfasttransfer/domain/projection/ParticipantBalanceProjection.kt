package com.sokkun.smallfasttransfer.domain.projection

import java.math.BigDecimal

interface ParticipantBalanceProjection {
    val totalBalance: BigDecimal
    val participantId: Long
    val currencyId: Long
}