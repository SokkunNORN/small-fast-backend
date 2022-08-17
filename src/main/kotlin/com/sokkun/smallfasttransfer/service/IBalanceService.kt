package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.BalanceReq
import com.sokkun.smallfasttransfer.api.response.BalanceRes
import com.sokkun.smallfasttransfer.api.response.TotalBalanceRes
import com.sokkun.smallfasttransfer.domain.model.Balance
import java.math.BigDecimal

interface IBalanceService {
    fun getByUserId(id: Long): List<BalanceRes>
    fun getTotalBalanceOfParticipantId(id: Long): List<TotalBalanceRes>
    fun create(balance: BalanceReq): Balance
}