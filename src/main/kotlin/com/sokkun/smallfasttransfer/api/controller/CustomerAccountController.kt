package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.response.CustomerAccountRes
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.service.ICustomerAccountService
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/participant/account")
class CustomerAccountController(
    private val service: ICustomerAccountService
) {

    @GetMapping("/{id}")
    fun getByParticipant(@PathVariable id: Long): ResponseWrapper<List<CustomerAccountRes>> =
        ok(service.getByParticipant(id))
}