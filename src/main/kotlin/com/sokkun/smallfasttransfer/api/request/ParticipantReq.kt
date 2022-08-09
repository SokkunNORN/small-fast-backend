package com.sokkun.smallfasttransfer.api.request

data class ParticipantReq(
    val fullName: String? = null,
    val shortName: String? = null,
    val participantCode: String? = null,
    val bicfiCode: String? = null,
    val bankCode: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val address: String? = null,
    val statusId: Long? = null
)
