package com.sokkun.smallfasttransfer.api.response

import com.sokkun.smallfasttransfer.domain.model.CurrencyType
import com.sokkun.smallfasttransfer.domain.model.TransactionStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionRes(
    val id: Long,
    val transactionCode: String,
    val senderBank: ParticipantShortRes,
    val senderAccount: ParticipantUserShortRes,
    val receiverBank: ParticipantShortRes,
    val receiverAccount: ParticipantUserShortRes,
    val amount: BigDecimal,
    val currency: CurrencyType,
    val message: String?,
    val status: TransactionStatus,
    val createdAt: LocalDateTime?,
    val sentAt: LocalDateTime?,
    val settledAt: LocalDateTime?
)