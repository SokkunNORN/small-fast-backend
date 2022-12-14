package com.sokkun.smallfasttransfer.api.response

import com.sokkun.smallfasttransfer.domain.model.ParticipantUserStatus

data class ParticipantUserRes(
    val id: Long,
    val fullName: String,
    val username: String,
    val phone: String?,
    val email: String?,
    val status: ParticipantUserStatus?,
    val participant: ParticipantRes?,
    val createdAt: String?,
    val updatedAt: String?
)

data class ParticipantUserShortRes(
    val id: Long,
    val fullName: String,
    val username: String
)
