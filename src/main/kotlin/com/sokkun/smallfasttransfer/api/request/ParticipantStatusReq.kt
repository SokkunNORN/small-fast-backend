package com.sokkun.smallfasttransfer.api.request

import com.sokkun.smallfasttransfer.common.getOrElseThrow

data class ParticipantStatusReq(
    val name: String?,
    val description: String? = null
) {
    init {
        getOrElseThrow("name", name)
    }
}
