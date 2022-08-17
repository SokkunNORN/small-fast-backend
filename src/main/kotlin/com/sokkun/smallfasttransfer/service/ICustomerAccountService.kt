package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.response.CustomerAccountRes
import com.sokkun.smallfasttransfer.domain.model.CustomerAccount

interface ICustomerAccountService {
    fun getByParticipant(id: Long): List<CustomerAccountRes>
}