package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.TransactionReq
import com.sokkun.smallfasttransfer.api.request.filter.TransactionFilterReq
import com.sokkun.smallfasttransfer.api.response.TransactionRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.service.ITransactionService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionController(
    private val service: ITransactionService
) {
    @PostMapping()
    fun create(@RequestBody transactionReq: TransactionReq): ResponseWrapper<TransactionRes> =
        ok(service.create(transactionReq))

    @PostMapping("/send/{id}")
    fun sentTransaction(@PathVariable id: Long): ResponseWrapper<TransactionRes> = ok(service.send(id))

    @GetMapping("/pending")
    fun getPending(filterReq: TransactionFilterReq, pageable: Pageable): ResponseWrapper<PageResponse<TransactionRes>> =
        ok(service.getPendingTransaction(filterReq, pageable))

    @GetMapping("/sent")
    fun getSent(filterReq: TransactionFilterReq, pageable: Pageable): ResponseWrapper<PageResponse<TransactionRes>> =
        ok(service.getSentTransaction(filterReq, pageable))

    @GetMapping("/history")
    fun getHistory(filterReq: TransactionFilterReq, pageable: Pageable): ResponseWrapper<PageResponse<TransactionRes>> =
        ok(service.getTransactionHistory(filterReq, pageable))


    @GetMapping("/log")
    fun getLog(filterReq: TransactionFilterReq, pageable: Pageable): ResponseWrapper<PageResponse<TransactionRes>> =
        ok(service.getTransactionLog(filterReq, pageable))

    @GetMapping("/settlement")
    fun settlement(): ResponseWrapper<List<TransactionRes>> = ok(service.settlement())
}