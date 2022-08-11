package com.sokkun.smallfasttransfer.api.request

import com.sokkun.smallfasttransfer.common.getOrElseThrow

data class CurrencyTypeReq(
    val name: String?,
    val description: String? = null
) {
    init {
        getOrElseThrow("name", name)
    }
}
