package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.domain.model.TransactionStatus
import com.sokkun.smallfasttransfer.service.ITransactionStatusService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transaction/status")
class TransactionStatusController(
    private val service: ITransactionStatusService
) {
    @GetMapping
    fun getAll(): ResponseWrapper<List<TransactionStatus>> = ok(service.getAll())
}