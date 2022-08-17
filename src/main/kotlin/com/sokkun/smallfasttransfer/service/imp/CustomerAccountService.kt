package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.response.CustomerAccountRes
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.domain.model.CustomerAccount
import com.sokkun.smallfasttransfer.domain.spec.CustomerAccountSpec
import com.sokkun.smallfasttransfer.repository.CustomerAccountRepository
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.service.ICustomerAccountService
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class CustomerAccountService(
    private val customerAccountRepo: CustomerAccountRepository,
    private val participantRepo: ParticipantRepository
) : ICustomerAccountService {
    override fun getByParticipant(id: Long): List<CustomerAccountRes> {
        getOrElseThrow("Participant", id, participantRepo::findById)

        val participantSpec = CustomerAccountSpec.getByParticipant(id)
        val specification = Specification.where(participantSpec)
        val all = customerAccountRepo.findAll(specification)

        return all.map { it.toResponse() }
    }
}