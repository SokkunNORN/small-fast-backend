package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.CurrencyTypeReq
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.domain.model.CurrencyType
import com.sokkun.smallfasttransfer.service.ICurrencyTypeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/currency")
class CurrencyTypeController(
    private val service: ICurrencyTypeService
) {

    @GetMapping
    fun getAll(): ResponseWrapper<List<CurrencyType>> = ok(service.getAll())

    @PostMapping
    fun create(@RequestBody currencyTypeReq: CurrencyTypeReq): ResponseWrapper<CurrencyType> =
        ok(service.create(currencyTypeReq))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody currencyTypeReq: CurrencyTypeReq): ResponseWrapper<CurrencyType> =
        ok(service.update(id, currencyTypeReq))
}