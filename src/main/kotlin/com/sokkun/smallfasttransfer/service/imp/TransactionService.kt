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
import com.sokkun.smallfasttransfer.enum.TransactionStatusEnum.Companion.VALID_HISTORY
import com.sokkun.smallfasttransfer.enum.TransactionStatusEnum.Companion.VALID_LOG
import com.sokkun.smallfasttransfer.enum.TransactionStatusEnum.SIGNED_AND_SENT
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
        val statusIds = listOf(PENDING.id)
        val searchSpec = filterReq?.q?.let { TransactionSpec.genSearchSpec(it.lowercase()) }
        val senderBankSpec = filterReq?.senderBankId?.let { TransactionSpec.genFilterBySenderBank(it) }
        val receiverBankSpec = filterReq?.receiverBankId?.let { TransactionSpec.genFilterByReceiverBank(it) }
        val currencySpec = filterReq?.currencyId?.let { TransactionSpec.genFilterByCurrency(it) }
        val statusSpec = TransactionSpec.genFilterByStatus(statusIds)

        val specification = Specification.where(statusSpec)
            .and(searchSpec)
            .and(senderBankSpec)
            .and(receiverBankSpec)
            .and(currencySpec)

        return transactionRepo.findAll(specification, pageable).map { it.toResponse() }.toPageResponse()
    }

    override fun send(id: Long): TransactionRes {
        val transaction = getOrElseThrow("Transaction", id, transactionRepo::findById)
        val status = getOrElseThrow("Transaction status", SIGNED_AND_SENT.id, transactionStatusRepo::findById)
        if (transaction.status.id != PENDING.id) {
            throw ClientErrorException(ErrorCode.INVALID_STATUS, "Transaction id[${transaction.id}]")
        }
        transaction.status = status

        return transactionRepo.save(transaction).toResponse()
    }

    override fun getSentTransaction(
        filterReq: TransactionFilterReq?,
        pageable: Pageable
    ): PageResponse<TransactionRes> {
        pageable.checkingSortFields(Transaction::class.java)
        val statusIds = listOf(SIGNED_AND_SENT.id)
        val searchSpec = filterReq?.q?.let { TransactionSpec.genSearchSpec(it.lowercase()) }
        val senderBankSpec = filterReq?.senderBankId?.let { TransactionSpec.genFilterBySenderBank(it) }
        val receiverBankSpec = filterReq?.receiverBankId?.let { TransactionSpec.genFilterByReceiverBank(it) }
        val currencySpec = filterReq?.currencyId?.let { TransactionSpec.genFilterByCurrency(it) }
        val statusSpec = TransactionSpec.genFilterByStatus(statusIds)

        val specification = Specification.where(statusSpec)
            .and(searchSpec)
            .and(senderBankSpec)
            .and(receiverBankSpec)
            .and(currencySpec)

        return transactionRepo.findAll(specification, pageable).map { it.toResponse() }.toPageResponse()
    }

    override fun getTransactionHistory(
        filterReq: TransactionFilterReq?,
        pageable: Pageable
    ): PageResponse<TransactionRes> {
        pageable.checkingSortFields(Transaction::class.java)
        val statusIds = VALID_HISTORY
        val searchSpec = filterReq?.q?.let { TransactionSpec.genSearchSpec(it.lowercase()) }
        val senderBankSpec = filterReq?.senderBankId?.let { TransactionSpec.genFilterBySenderBank(it) }
        val receiverBankSpec = filterReq?.receiverBankId?.let { TransactionSpec.genFilterByReceiverBank(it) }
        val currencySpec = filterReq?.currencyId?.let { TransactionSpec.genFilterByCurrency(it) }
        val statusSpec = TransactionSpec.genFilterByStatus(statusIds)

        val specification = Specification.where(statusSpec)
            .and(searchSpec)
            .and(senderBankSpec)
            .and(receiverBankSpec)
            .and(currencySpec)

        return transactionRepo.findAll(specification, pageable).map { it.toResponse() }.toPageResponse()
    }

    override fun getTransactionLog(
        filterReq: TransactionFilterReq?,
        pageable: Pageable
    ): PageResponse<TransactionRes> {
        pageable.checkingSortFields(Transaction::class.java)
        val statusIds = VALID_LOG
        val searchSpec = filterReq?.q?.let { TransactionSpec.genSearchSpec(it.lowercase()) }
        val senderBankSpec = filterReq?.senderBankId?.let { TransactionSpec.genFilterBySenderBank(it) }
        val receiverBankSpec = filterReq?.receiverBankId?.let { TransactionSpec.genFilterByReceiverBank(it) }
        val currencySpec = filterReq?.currencyId?.let { TransactionSpec.genFilterByCurrency(it) }
        val statusSpec = TransactionSpec.genFilterByStatus(statusIds)

        val specification = Specification.where(statusSpec)
            .and(searchSpec)
            .and(senderBankSpec)
            .and(receiverBankSpec)
            .and(currencySpec)

        return transactionRepo.findAll(specification, pageable).map { it.toResponse() }.toPageResponse()
    }

    fun getBalanceOfParticipantUser (userId: Long, currencyId: Long): BigDecimal {
        val userSpec = BalanceSpec.genFindByUserIdAndCurrencyId(userId, currencyId)
        val specification = Specification.where(userSpec)

        val accountBalance = balanceRepo.findOne(specification)
        if (accountBalance.isEmpty) throw ClientErrorException(ErrorCode.ID_NOT_FOUND, "Account Balance")

        return accountBalance.get().balance
    }
}