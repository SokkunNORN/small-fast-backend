package com.sokkun.smallfasttransfer.api.request

import com.sokkun.smallfasttransfer.common.getOrElseThrow

data class ParticipantUserReq(
    val fullName: String?,
    val username: String?,
    val phone: String? = null,
    val email: String? = null,
    val statusId: Long?,
    val participantId: Long?
) {
    init {
        getOrElseThrow("fullName", fullName)
        getOrElseThrow("username", username)
        getOrElseThrow("statusId", statusId)
        getOrElseThrow("participantId", participantId)
    }
}
