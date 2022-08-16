package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.TransactionReq
import com.sokkun.smallfasttransfer.api.request.filter.TransactionFilterReq
import com.sokkun.smallfasttransfer.api.response.TransactionRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import org.springframework.data.domain.Pageable

interface ITransactionService {
    fun create(transactionReq: TransactionReq): TransactionRes
    fun getPendingTransaction(filterReq: TransactionFilterReq?, pageable: Pageable): PageResponse<TransactionRes>
    fun send(id: Long): TransactionRes
    fun getSentTransaction(senderBankId: Long): PageResponse<TransactionRes>
    fun getOutGoingTransaction(senderBankId: Long): PageResponse<TransactionRes>
    fun getIncomingTransaction(receiverBankId: Long): PageResponse<TransactionRes>
    fun getTransactionHistory(participantId: Long): PageResponse<TransactionRes>
}