package com.sokkun.smallfasttransfer.api.request


data class ParticipantReq(
    val fullName: String,
    val shortName: String,
    val participantCode: String,
    val bicfiCode: String,
    val bankCode: String,
    val phone: String?,
    val email: String?,
    val address: String?,
    val statusId: Long
)
