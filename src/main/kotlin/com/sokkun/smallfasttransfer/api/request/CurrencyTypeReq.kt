package com.sokkun.smallfasttransfer.api.request

import com.sokkun.smallfasttransfer.common.getOrElseThrow

data class CurrencyTypeReq(
    val code: String?,
    val name: String?
) {
    init {
        getOrElseThrow("code", code)
        getOrElseThrow("name", name)
    }
}
