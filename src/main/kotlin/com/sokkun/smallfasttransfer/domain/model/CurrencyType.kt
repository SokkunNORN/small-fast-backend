package com.sokkun.smallfasttransfer.domain.model

import javax.persistence.*

@Entity
@Table(name = "currency_type")
data class CurrencyType(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCurrencyType")
    @SequenceGenerator(name = "seqCurrencyType", sequenceName = "SEQ_CURRENCY_TYPE", initialValue = 10, allocationSize = 10)
    val id: Long = 0L,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String?
)
