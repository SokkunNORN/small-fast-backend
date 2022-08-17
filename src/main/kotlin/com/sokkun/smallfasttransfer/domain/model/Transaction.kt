package com.sokkun.smallfasttransfer.domain.model

import com.sokkun.smallfasttransfer.api.response.TransactionRes
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "transaction")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTransaction")
    @SequenceGenerator(name = "seqTransaction", sequenceName = "SEQ_TRANSACTION", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "transaction_code")
    var transactionCode: String,

    @Column(name = "amount")
    val amount: BigDecimal,

    @Column(name = "message")
    val message: String? = null,

    @Column(name = "created_at")
    @CreationTimestamp
    val createdAt: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "sent_at")
    var sentAt: LocalDateTime? = null,

    @Column(name = "settled_at")
    var settledAt: LocalDateTime? = null
) {
    @ManyToOne
    @JoinColumn(name = "sender_bank_id")
    lateinit var senderBank: Participant

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    lateinit var senderAccount: ParticipantUser

    @ManyToOne
    @JoinColumn(name = "receiver_bank_id")
    lateinit var receiverBank: Participant

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    lateinit var receiverAccount: ParticipantUser

    @ManyToOne
    @JoinColumn(name = "currency_id")
    lateinit var currency: CurrencyType

    @ManyToOne
    @JoinColumn(name = "status_id")
    lateinit var status: TransactionStatus

    fun toResponse(): TransactionRes {
        return TransactionRes(
            id = this.id,
            transactionCode = this.transactionCode,
            senderBank = senderBank.toShortResponse(),
            senderAccount = senderAccount.toShortResponse(),
            receiverBank = receiverBank.toShortResponse(),
            receiverAccount = receiverAccount.toShortResponse(),
            amount = this.amount,
            currency = this.currency,
            status = status,
            message = this.message,
            createdAt = this.createdAt,
            sentAt = this.sentAt,
            settledAt = this.settledAt
        )
    }
}
