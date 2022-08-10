package com.sokkun.smallfasttransfer.api.request

import com.sokkun.smallfasttransfer.common.getOrElseThrow

data class ParticipantReq(
    val fullName: String,
    val shortName: String,
    val participantCode: String,
    val bicfiCode: String,
    val bankCode: String,
    val phone: String? = null,
    val email: String? = null,
    val address: String? = null,
    val statusId: Long
){
    init {
        getOrElseThrow("fullName", fullName)
        getOrElseThrow("shortName", shortName)
        getOrElseThrow("participantCode", participantCode)
        getOrElseThrow("bicfiCode", bicfiCode)
        getOrElseThrow("bankCode", bankCode)
        getOrElseThrow("statusId", statusId)
    }
}
