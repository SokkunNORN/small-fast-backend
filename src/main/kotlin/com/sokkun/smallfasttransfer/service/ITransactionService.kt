package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.TransactionReq
import com.sokkun.smallfasttransfer.api.request.filter.TransactionFilterReq
import com.sokkun.smallfasttransfer.api.response.TransactionRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import org.springframework.data.domain.Pageable

interface ITransactionService {
    fun create(transactionReq: TransactionReq): TransactionRes
    fun getPendingTransaction(filterReq: TransactionFilterReq?, pageable: Pageable): PageResponse<TransactionRes>
    fun send(id: Long): TransactionRes
    fun getSentTransaction(filterReq: TransactionFilterReq?, pageable: Pageable): PageResponse<TransactionRes>
    fun getTransactionHistory(filterReq: TransactionFilterReq?, pageable: Pageable): PageResponse<TransactionRes>
    fun getTransactionLog(filterReq: TransactionFilterReq?, pageable: Pageable): PageResponse<TransactionRes>
    fun settlement(): List<TransactionRes>
}