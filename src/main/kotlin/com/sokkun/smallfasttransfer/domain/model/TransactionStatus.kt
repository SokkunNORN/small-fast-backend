package com.sokkun.smallfasttransfer.domain.model

import javax.persistence.*

@Entity
@Table(name = "transaction_status")
data class TransactionStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqTransactionStatus")
    @SequenceGenerator(name = "seqTransactionStatus", sequenceName = "SEQ_TRANSACTION_STATUS", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String?
)
