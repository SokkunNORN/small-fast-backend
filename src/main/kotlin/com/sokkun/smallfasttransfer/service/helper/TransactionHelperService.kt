package com.sokkun.smallfasttransfer.service.helper

import com.sokkun.smallfasttransfer.domain.model.Transaction
import com.sokkun.smallfasttransfer.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class TransactionHelperService(
    private val transactionRepo: TransactionRepository
) {
    fun saveWithTransactionCode(transaction: Transaction) : Transaction {
        var indicator = "AA"
        if (transaction.id in 10000000..19999999) indicator = "AB"
        if (transaction.id in 20000000..29999999) indicator = "AC"
        if (transaction.id in 30000000..39999999) indicator = "AD"
        transaction.transactionCode = "$indicator${String.format("%07d", transaction.id)}"

        return transactionRepo.save(transaction)
    }
}