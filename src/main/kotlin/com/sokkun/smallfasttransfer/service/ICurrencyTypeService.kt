package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.CurrencyTypeReq
import com.sokkun.smallfasttransfer.domain.model.CurrencyType

interface ICurrencyTypeService {
    fun getAll(): List<CurrencyType>
    fun create(currencyTypeReq: CurrencyTypeReq): CurrencyType
    fun update(id: Long, currencyTypeReq: CurrencyTypeReq): CurrencyType
}