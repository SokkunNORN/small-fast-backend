package com.sokkun.smallfasttransfer.service.helper

import com.sokkun.smallfasttransfer.api.exception.ClientErrorException
import com.sokkun.smallfasttransfer.api.exception.handler.ErrorCode
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.domain.model.Transaction
import com.sokkun.smallfasttransfer.domain.model.TransactionStatus
import com.sokkun.smallfasttransfer.domain.spec.BalanceSpec
import com.sokkun.smallfasttransfer.repository.BalanceRepository
import com.sokkun.smallfasttransfer.repository.TransactionRepository
import com.sokkun.smallfasttransfer.repository.TransactionStatusRepository
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.math.BigDecimal
import com.sokkun.smallfasttransfer.enum.TransactionStatusEnum.SETTLED
import com.sokkun.smallfasttransfer.enum.TransactionStatusEnum.UNSETTLED
import java.time.LocalDateTime

@Service
class TransactionHelperService(
    private val transactionRepo: TransactionRepository,
    private val transactionStatusRepo: TransactionStatusRepository,
    private val balanceRepo: BalanceRepository
) {
    fun saveWithTransactionCode(transaction: Transaction) : Transaction {
        var indicator = "AA"
        if (transaction.id in 10000000..19999999) indicator = "AB"
        if (transaction.id in 20000000..29999999) indicator = "AC"
        if (transaction.id in 30000000..39999999) indicator = "AD"
        transaction.transactionCode = "$indicator${String.format("%07d", transaction.id)}"

        return transactionRepo.save(transaction)
    }

    fun getBalanceOfParticipantUser (userId: Long, currencyId: Long): BigDecimal {
        val userSpec = BalanceSpec.genFindByUserIdAndCurrencyId(userId, currencyId)
        val specification = Specification.where(userSpec)

        val accountBalance = balanceRepo.findOne(specification)
        if (accountBalance.isEmpty) throw ClientErrorException(ErrorCode.ID_NOT_FOUND, "Account Balance")

        return accountBalance.get().balance
    }

    fun getStatus(senderBalance: BigDecimal, transactionBalance: BigDecimal): TransactionStatus {
        val settled = getOrElseThrow("Transaction status", SETTLED.id, transactionStatusRepo::findById)
        val unsettled = getOrElseThrow("Transaction status", UNSETTLED.id, transactionStatusRepo::findById)

        if (senderBalance < transactionBalance) return unsettled
        return settled
    }

    fun settlement(transaction: Transaction): Transaction {
        val senderBalance = getBalanceOfParticipantUser(transaction.senderAccount.id, transaction.currency.id)
        val status = getStatus(senderBalance, transaction.amount)

        if (status.id == SETTLED.id) {
            val senderBalanceSpec = BalanceSpec.genFindByUserIdAndCurrencyId(
                transaction.senderAccount.id,
                transaction.currency.id
            )
            val senderSpecification = Specification.where(senderBalanceSpec)
            val receiverBalanceSpec = BalanceSpec.genFindByUserIdAndCurrencyId(
                transaction.senderAccount.id,
                transaction.currency.id
            )
            val receiverSpecification = Specification.where(receiverBalanceSpec)

            val senderAccountBalance = balanceRepo.findOne(senderSpecification)
            val receiverAccountBalance = balanceRepo.findOne(receiverSpecification)
            if (senderAccountBalance.isEmpty) throw ClientErrorException(ErrorCode.ID_NOT_FOUND, "Sender Account Balance")
            if (receiverAccountBalance.isEmpty) throw ClientErrorException(ErrorCode.ID_NOT_FOUND, "Receiver Account Balance")

            senderAccountBalance.get().balance = senderAccountBalance.get().balance.minus(transaction.amount)
            receiverAccountBalance.get().balance = receiverAccountBalance.get().balance.plus(transaction.amount)
        }

        transaction.status = status
        transaction.settledAt = LocalDateTime.now()

        return transactionRepo.save(transaction)
    }
}