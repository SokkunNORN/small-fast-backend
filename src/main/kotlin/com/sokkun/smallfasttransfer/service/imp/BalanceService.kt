package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.BalanceReq
import com.sokkun.smallfasttransfer.api.response.BalanceRes
import com.sokkun.smallfasttransfer.api.response.TotalBalanceRes
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.domain.model.Balance
import com.sokkun.smallfasttransfer.domain.projection.ParticipantBalanceProjection
import com.sokkun.smallfasttransfer.domain.spec.BalanceSpec
import com.sokkun.smallfasttransfer.repository.BalanceRepository
import com.sokkun.smallfasttransfer.repository.CurrencyTypeRepository
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.repository.ParticipantUserRepository
import com.sokkun.smallfasttransfer.service.IBalanceService
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BalanceService(
    private val balanceRepo: BalanceRepository,
    private val currencyRepo: CurrencyTypeRepository,
    private val participantRepo: ParticipantRepository,
    private val participantUserRepo: ParticipantUserRepository
) : IBalanceService {
    override fun getByUserId(id: Long): List<BalanceRes> {
        getOrElseThrow("Participant User", id, participantUserRepo::findById)
        val userSpec = BalanceSpec.genFilterUserId(id)
        val specification = Specification.where(userSpec)

        val all = balanceRepo.findAll(specification)

        return all.map{ it.toResponse() }
    }

    override fun getTotalBalanceOfParticipantId(id: Long): List<TotalBalanceRes> {
        getOrElseThrow("Participant", id, participantRepo::findById)

        val balance = balanceRepo.findTotalBalanceParticipant(id)

        return balance.map { toResponseTotalBalance(it) }
    }

    override fun create(balance: BalanceReq): Balance {
        val currency = getOrElseThrow("Currency", balance.currencyId!!, currencyRepo::findById)
        val user = getOrElseThrow("Participant User", balance.participantUserId!!, participantUserRepo::findById)
        val newBalance = Balance(
            0L,
            balance.balance!!
        ).apply {
            currencyType = currency
            participantUser = user
        }

        return balanceRepo.save(newBalance)
    }

    fun toResponseTotalBalance(balance: ParticipantBalanceProjection): TotalBalanceRes {
        val participant = getOrElseThrow("Participant", balance.participantId, participantRepo::findById)
        val currency = getOrElseThrow("Currency", balance.currencyId, currencyRepo::findById)

        return TotalBalanceRes(
            totalBalance = balance.totalBalance,
            participant = participant.toShortResponse(),
            currency = currency
        )
    }
}