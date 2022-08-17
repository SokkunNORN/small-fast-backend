package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantUserReq
import com.sokkun.smallfasttransfer.api.request.filter.ParticipantUserFilterRes
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import com.sokkun.smallfasttransfer.common.checkingSortFields
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.common.toPageResponse
import com.sokkun.smallfasttransfer.domain.model.Balance
import com.sokkun.smallfasttransfer.domain.model.ParticipantUser
import com.sokkun.smallfasttransfer.domain.spec.ParticipantUserSpec
import com.sokkun.smallfasttransfer.enum.CurrencyTypeEnum.KHR
import com.sokkun.smallfasttransfer.enum.CurrencyTypeEnum.USD
import com.sokkun.smallfasttransfer.repository.CurrencyTypeRepository
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.repository.ParticipantUserRepository
import com.sokkun.smallfasttransfer.repository.ParticipantUserStatusRepository
import com.sokkun.smallfasttransfer.service.IParticipantUserService
import com.sokkun.smallfasttransfer.service.helper.BalanceHelperService
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ParticipantUserService(
    private val partUserRepo: ParticipantUserRepository,
    private val partUserStatusRepo: ParticipantUserStatusRepository,
    private val partRepo: ParticipantRepository,
    private val balanceHelperService: BalanceHelperService
) : IParticipantUserService {
    override fun getAllUser(
        filterRes: ParticipantUserFilterRes?,
        pageable: Pageable
    ): PageResponse<ParticipantUserRes> {
        pageable.checkingSortFields(ParticipantUser::class.java)
        val searchSpec = filterRes?.q?.let { ParticipantUserSpec.genSearchSpec(it.lowercase()) }
        val statusSpec = filterRes?.statusId?.let { ParticipantUserSpec.genFilterByStatusSpec(it) }
        val participantSpec = filterRes?.participantId?.let { ParticipantUserSpec.genFilterByParticipantSpec(it) }
        val specification = Specification.where(searchSpec).and(statusSpec).and(participantSpec)

        val all = partUserRepo.findAll(specification, pageable)

        return all.map { it.toResponse() }.toPageResponse()
    }

    override fun getUserById(id: Long): ParticipantUserRes {
        val user = getOrElseThrow("Participant User", id, partUserRepo::findById)

        return user.toResponse()
    }

    override fun createUser(participantUserReq: ParticipantUserReq): ParticipantUserRes {
        val status = getOrElseThrow("Participant User Status id", participantUserReq.statusId!!, partUserStatusRepo::findById)
        val participant = getOrElseThrow("Participant id", participantUserReq.participantId!!, partRepo::findById)

        val user = ParticipantUser(
            0,
            fullName = participantUserReq.fullName!!,
            username = participantUserReq.username!!,
            phone = participantUserReq.phone,
            email = participantUserReq.email
        ).apply {
            this.status = status
            this.participant = participant
        }

        val newUser = partUserRepo.save(user)

        balanceHelperService.createBalance(newUser)

        return newUser.toResponse()
    }

    override fun updateUser(id: Long, participantUserReq: ParticipantUserReq): ParticipantUserRes {
        getUserById(id)
        val status = getOrElseThrow("Participant User Status id", participantUserReq.statusId!!, partUserStatusRepo::findById)
        val participant = getOrElseThrow("Participant id", participantUserReq.participantId!!, partRepo::findById)

        val user = ParticipantUser(
            id,
            fullName = participantUserReq.fullName!!,
            username = participantUserReq.username!!,
            phone = participantUserReq.phone,
            email = participantUserReq.email
        ).apply {
            this.status = status
            this.participant = participant
        }

        val newUser = partUserRepo.save(user)

        return newUser.toResponse()
    }

    override fun deleteUser(id: Long): String {
        getUserById(id)

        partUserRepo.deleteById(id)

        return "The Participant User Id[$id] is deleted successfully!!"
    }
}