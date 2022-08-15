package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.TransactionReq
import com.sokkun.smallfasttransfer.api.response.TransactionRes

interface ITransactionService {
    fun create(transactionReq: TransactionReq): TransactionRes
    fun getPendingTransaction(senderBankId: Long): List<TransactionRes>
    fun send(id: Long): TransactionRes
    fun getSentTransaction(senderBankId: Long): List<TransactionRes>
    fun getOutGoingTransaction(senderBankId: Long): List<TransactionRes>
    fun getIncomingTransaction(receiverBankId: Long): List<TransactionRes>
    fun getTransactionHistory(participantId: Long): List<TransactionRes>
}