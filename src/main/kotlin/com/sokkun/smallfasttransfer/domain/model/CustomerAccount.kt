package com.sokkun.smallfasttransfer.domain.model

import com.sokkun.smallfasttransfer.api.response.CustomerAccountRes
import com.sokkun.smallfasttransfer.api.response.CustomerAccountShortRes
import javax.persistence.*

@Entity
@Table(name = "customer_account")
data class CustomerAccount (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCustomerAccount")
    @SequenceGenerator(name = "seqCustomerAccount", sequenceName = "SEQ_CUSTOMER_ACCOUNT", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "code")
    val code: String,

    @Column(name = "customer_name")
    val customerName: String
) {
    @ManyToOne
    @JoinColumn(name = "participant_id")
    lateinit var participant: Participant

    fun toResponse(): CustomerAccountRes {
        return CustomerAccountRes(
            id = this.id,
            code = this.code,
            customerName = this.customerName,
            participant = participant.toShortResponse()
        )
    }

    fun toShortResponse(): CustomerAccountShortRes {
        return CustomerAccountShortRes(
            id = this.id,
            code = this.code,
            customerName = this.customerName
        )
    }
}