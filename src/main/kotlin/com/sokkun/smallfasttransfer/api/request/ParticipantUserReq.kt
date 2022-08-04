package com.sokkun.smallfasttransfer.api.request

data class ParticipantUserReq(
    val fullName: String,
    val username: String,
    val phone: String?,
    val email: String?,
    val statusId: Long,
    val participantId: Long
)
