package com.sokkun.smallfasttransfer.api.response

data class CustomerAccountRes(
    val id: Long,
    val code: String,
    val customerName: String,
    val participant: ParticipantShortRes
)

data class CustomerAccountShortRes (
    val id: Long,
    val code: String,
    val customerName: String
)
