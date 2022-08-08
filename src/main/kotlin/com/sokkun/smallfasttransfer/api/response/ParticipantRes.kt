package com.sokkun.smallfasttransfer.api.response

import com.sokkun.smallfasttransfer.model.ParticipantStatus
import java.time.LocalDateTime

data class ParticipantRes(
    val id: Long,
    val fullName: String,
    val shortName: String,
    val participantCode: String,
    val bicfiCode: String,
    val bankCode: String,
    val phone: String?,
    val email: String?,
    val address: String?,
    val status: ParticipantStatus?,
    val createdAt: String,
    val updatedAt: String
)