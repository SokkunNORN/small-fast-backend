package com.sokkun.smallfasttransfer.service.helper

import com.sokkun.smallfasttransfer.api.request.BalanceReq
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.domain.model.Balance
import com.sokkun.smallfasttransfer.domain.model.ParticipantUser
import com.sokkun.smallfasttransfer.enum.CurrencyTypeEnum.USD
import com.sokkun.smallfasttransfer.enum.CurrencyTypeEnum.KHR
import com.sokkun.smallfasttransfer.repository.CurrencyTypeRepository
import com.sokkun.smallfasttransfer.service.imp.BalanceService
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BalanceHelperService(
    private val balanceService: BalanceService,
    private val currencyTypeRepo: CurrencyTypeRepository
) {
    fun createBalance(user: ParticipantUser) {
        getOrElseThrow("Currency", KHR.id, currencyTypeRepo::findById)
        getOrElseThrow("Currency", USD.id, currencyTypeRepo::findById)

        val khrBalance = BalanceReq(
            balance = BigDecimal(0),
            participantUserId = user.id,
            currencyId = KHR.id
        )
        val usdBalance = BalanceReq(
            balance = BigDecimal(0),
            participantUserId = user.id,
            currencyId = USD.id
        )
        balanceService.create(khrBalance)
        balanceService.create(usdBalance)
    }
}