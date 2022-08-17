package com.sokkun.smallfasttransfer.domain.model

import com.sokkun.smallfasttransfer.api.response.BalanceRes
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "balance")
data class Balance(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqBalance")
    @SequenceGenerator(name = "seqBalance", sequenceName = "SEQ_BALANCE", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "balance")
    var balance: BigDecimal
) {
    @ManyToOne
    @JoinColumn(name = "currency_id")
    lateinit var currencyType: CurrencyType

    @ManyToOne
    @JoinColumn(name = "participant_user_id")
    lateinit var participantUser: ParticipantUser

    fun toResponse(): BalanceRes {
        return BalanceRes(
            id = this.id,
            balance = this.balance,
            currency = this.currencyType,
            user = participantUser.toShortResponse(),
            participant = participantUser.participant.toShortResponse()
        )
    }
}
