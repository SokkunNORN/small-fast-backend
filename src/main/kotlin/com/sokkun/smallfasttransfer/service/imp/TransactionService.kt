package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.exception.ClientErrorException
import com.sokkun.smallfasttransfer.api.exception.handler.ErrorCode
import com.sokkun.smallfasttransfer.api.request.TransactionReq
import com.sokkun.smallfasttransfer.api.request.filter.TransactionFilterReq
import com.sokkun.smallfasttransfer.api.response.TransactionRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import com.sokkun.smallfasttransfer.common.checkingSortFields
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.common.toPageResponse
import com.sokkun.smallfasttransfer.domain.model.Transaction
import com.sokkun.smallfasttransfer.domain.spec.BalanceSpec
import com.sokkun.smallfasttransfer.domain.spec.TransactionSpec
import com.sokkun.smallfasttransfer.enum.TransactionStatusEnum.PENDING
import com.sokkun.smallfasttransfer.repository.*
import com.sokkun.smallfasttransfer.service.ITransactionService
import com.sokkun.smallfasttransfer.service.helper.TransactionHelperService
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransactionService(
    private val transactionHelper: TransactionHelperService,
    private val transactionRepo: TransactionRepository,
    private val transactionStatusRepo: TransactionStatusRepository,
    private val participantRepo: ParticipantRepository,
    private val participantUserRepo: ParticipantUserRepository,
    private val balanceRepo: BalanceRepository,
    private val currencyRepo: CurrencyTypeRepository
) : ITransactionService {
    override fun create(transactionReq: TransactionReq): TransactionRes {
        val transactionCode = "Null"
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

        val transactionStatus = getOrElseThrow("Transaction Status", PENDING.id, transactionStatusRepo::findById)

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
        val transactionSaved = transactionHelper.saveWithTransactionCode(transactionRepo.save(transaction))

        return transactionSaved.toResponse()
    }

    override fun getPendingTransaction(
        filterReq: TransactionFilterReq?,
        pageable: Pageable
    ): PageResponse<TransactionRes> {
        pageable.checkingSortFields(Transaction::class.java)
        val searchSpec = filterReq?.q?.let { TransactionSpec.genSearchWithEachStatusSpec(it.lowercase(), PENDING.id) }
        val senderBankSpec = filterReq?.senderBankId?.let { TransactionSpec.genFilterBySenderBank(it, PENDING.id) }
        val receiverBankSpec = filterReq?.receiverBankId?.let { TransactionSpec.genFilterByReceiverBank(it, PENDING.id) }
        val currencySpec = filterReq?.currencyId?.let { TransactionSpec.genFilterByCurrency(it, PENDING.id) }

        val specification = Specification.where(searchSpec).and(senderBankSpec).and(receiverBankSpec).and(currencySpec)

        return transactionRepo.findAll(specification, pageable).map { it.toResponse() }.toPageResponse()
    }

    override fun send(id: Long): TransactionRes {
        TODO("Not yet implemented")
    }

    override fun getSentTransaction(senderBankId: Long): PageResponse<TransactionRes> {
        TODO("Not yet implemented")
    }

    override fun getOutGoingTransaction(senderBankId: Long): PageResponse<TransactionRes> {
        TODO("Not yet implemented")
    }

    override fun getIncomingTransaction(receiverBankId: Long): PageResponse<TransactionRes> {
        TODO("Not yet implemented")
    }

    override fun getTransactionHistory(participantId: Long): PageResponse<TransactionRes> {
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