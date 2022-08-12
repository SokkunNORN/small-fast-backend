package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.domain.model.TransactionStatus
import com.sokkun.smallfasttransfer.repository.TransactionStatusRepository
import com.sokkun.smallfasttransfer.service.ITransactionStatusService
import org.springframework.stereotype.Service

@Service
class TransactionStatusService(
    private val transactionStatusRepo: TransactionStatusRepository
) : ITransactionStatusService {
    override fun getAll(): List<TransactionStatus> = transactionStatusRepo.findAll()
}