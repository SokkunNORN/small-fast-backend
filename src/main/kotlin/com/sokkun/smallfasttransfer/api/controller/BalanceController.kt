package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.response.BalanceRes
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.service.IBalanceService
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/balance")
class BalanceController(
    private val service: IBalanceService
) {
    @GetMapping("/user/{id}")
    fun getByUser(@PathVariable id: Long): ResponseWrapper<List<BalanceRes>> = ok(service.getByUserId(id))
}