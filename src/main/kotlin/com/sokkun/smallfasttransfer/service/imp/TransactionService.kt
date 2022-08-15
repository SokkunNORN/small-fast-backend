package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.exception.ClientErrorException
import com.sokkun.smallfasttransfer.api.exception.handler.ErrorCode
import com.sokkun.smallfasttransfer.api.request.TransactionReq
import com.sokkun.smallfasttransfer.api.response.TransactionRes
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.domain.model.Transaction
import com.sokkun.smallfasttransfer.domain.spec.BalanceSpec
import com.sokkun.smallfasttransfer.repository.*
import com.sokkun.smallfasttransfer.service.ITransactionService
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransactionService(
    private val transactionRepo: TransactionRepository,
    private val transactionStatusRepo: TransactionStatusRepository,
    private val participantRepo: ParticipantRepository,
    private val participantUserRepo: ParticipantUserRepository,
    private val balanceRepo: BalanceRepository,
    private val currencyRepo: CurrencyTypeRepository
) : ITransactionService {
    override fun create(transactionReq: TransactionReq): TransactionRes {
        val transactionCode = "AA0003912"
        if (transactionReq.senderAccountId!! == transactionReq.receiverAccountId!!) {
            throw ClientErrorException(
                ErrorCode.SENDER_AND_RECEIVER_BANK_EQUAL,
                "id[${transactionReq.receiverAccountId}]"
            )
        }
        val sender = getOrElseThrow("Sender Account", transactionReq.senderAccountId, participantUserRepo::findById)
        val receiver = getOrElseThrow("Receiver Account", transactionReq.receiverAccountId, participantUserRepo::findById)
        val currencyType = getOrElseThrow("Currency", transactionReq.currencyId!!, currencyRepo::findById)
        val senderParticipant = getOrElseThrow("Sender Bank", sender.participant.id, participantRepo::findById)
        val receiverParticipant = getOrElseThrow("Sender Bank", receiver.participant.id, participantRepo::findById)

        if (sender.participant.id == receiver.participant.id) {
            throw ClientErrorException(
                ErrorCode.SENDER_AND_RECEIVER_BANK_EQUAL,
                "of participant bank id[${sender.participant.id}]"
            )
        }

        val transactionStatus = getOrElseThrow("Transaction Status", 1, transactionStatusRepo::findById)

        val currentAmount = getBalanceOfParticipantUser(sender.id, currencyType.id)

        val transaction = Transaction(
            0,
            transactionCode = transactionCode,
            amount = transactionReq.amount!!,
            message = transactionReq.message,
            sentAt = null,
            settledAt = null
        ).apply {
            senderBank = senderParticipant
            senderAccount = sender
            receiverBank = receiverParticipant
            receiverAccount = receiver
            currency = currencyType
            status = transactionStatus
        }

        return transactionRepo.save(transaction).toResponse()
    }

    override fun getPendingTransaction(senderBankId: Long): List<TransactionRes> {
        TODO("Not yet implemented")
    }

    override fun send(id: Long): TransactionRes {
        TODO("Not yet implemented")
    }

    override fun getSentTransaction(senderBankId: Long): List<TransactionRes> {
        TODO("Not yet implemented")
    }

    override fun getOutGoingTransaction(senderBankId: Long): List<TransactionRes> {
        TODO("Not yet implemented")
    }

    override fun getIncomingTransaction(receiverBankId: Long): List<TransactionRes> {
        TODO("Not yet implemented")
    }

    override fun getTransactionHistory(participantId: Long): List<TransactionRes> {
        TODO("Not yet implemented")
    }

    fun getBalanceOfParticipantUser (userId: Long, currencyId: Long): BigDecimal {
        val userSpec = BalanceSpec.genFindByUserIdAndCurrencyId(userId, currencyId)
        val specification = Specification.where(userSpec)

        val accountBalance = balanceRepo.findOne(specification)
        if (accountBalance.isEmpty) throw ClientErrorException(ErrorCode.ID_NOT_FOUND, "Account Balance")

        return accountBalance.get().balance
    }
}