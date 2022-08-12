package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.domain.model.TransactionStatus

interface ITransactionStatusService {
    fun getAll(): List<TransactionStatus>
}