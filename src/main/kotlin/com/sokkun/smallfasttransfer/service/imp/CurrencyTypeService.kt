package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.CurrencyTypeReq
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.domain.model.CurrencyType
import com.sokkun.smallfasttransfer.repository.CurrencyTypeRepository
import com.sokkun.smallfasttransfer.service.ICurrencyTypeService
import org.springframework.stereotype.Service

@Service
class CurrencyTypeService(
    private val currencyTypeRepo: CurrencyTypeRepository
) : ICurrencyTypeService {
    override fun getAll(): List<CurrencyType> = currencyTypeRepo.findAll()

    override fun create(currencyTypeReq: CurrencyTypeReq): CurrencyType {
        val currency = CurrencyType(
            0L,
            currencyTypeReq.code!!,
            currencyTypeReq.name!!
        )

        return currencyTypeRepo.save(currency)
    }

    override fun update(id: Long, currencyTypeReq: CurrencyTypeReq): CurrencyType {
        getOrElseThrow("Currency Type", id, currencyTypeRepo::findById)

        val currency = CurrencyType(
            id,
            currencyTypeReq.code!!,
            currencyTypeReq.name!!
        )

        return currencyTypeRepo.save(currency)
    }
}