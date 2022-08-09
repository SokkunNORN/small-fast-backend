package com.sokkun.smallfasttransfer.api.request

data class ParticipantUserReq(
    val fullName: String? = null,
    val username: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val statusId: Long? = null,
    val participantId: Long? = null
)
