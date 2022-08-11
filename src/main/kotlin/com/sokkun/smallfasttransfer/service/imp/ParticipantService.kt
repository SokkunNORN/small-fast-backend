package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.filter.ParticipantFilterReq
import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import com.sokkun.smallfasttransfer.common.checkingSortFields
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.common.toPageResponse
import com.sokkun.smallfasttransfer.domain.model.Participant
import com.sokkun.smallfasttransfer.domain.spec.ParticipantSpec
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.repository.ParticipantStatusRepository
import com.sokkun.smallfasttransfer.service.IParticipantService
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val partRepo: ParticipantRepository,
    private val partStatusRepo: ParticipantStatusRepository
) : IParticipantService {
    override fun getAllParticipant(
        filterReq: ParticipantFilterReq?,
        pageable: Pageable
    ): PageResponse<ParticipantRes> {
        pageable.checkingSortFields(Participant::class.java)
        val searchSpec = filterReq?.q?.let { ParticipantSpec.genSearchSpec(it.lowercase()) }
        val statusSpec = filterReq?.statusId?.let { ParticipantSpec.genFilterParticipantStatusSpec(it) }
        val specification = Specification.where(searchSpec).and(statusSpec)
        println(filterReq?.dateFrom)

        val all = partRepo.findAll(specification, pageable)

        return all.map { it.toResponse() }.toPageResponse()
    }

    override fun getParticipantById(id: Long): ParticipantRes {
        val part = getOrElseThrow("Participant", id, partRepo::findById)

        return part.toResponse()
    }

    override fun createParticipant(participantReq: ParticipantReq): ParticipantRes {
        val status = getOrElseThrow("Participant Status", participantReq.statusId!!, partStatusRepo::findById)
        val part = Participant(
            0,
            fullName = participantReq.fullName!!,
            shortName = participantReq.shortName!!,
            participantCode = participantReq.participantCode!!,
            bicfiCode = participantReq.bicfiCode!!,
            bankCode = participantReq.bankCode!!,
            phone = participantReq.phone,
            email = participantReq.email,
            address = participantReq.address
        ).apply {
            this.status = status
        }

        val newPart = partRepo.save(part)

        return newPart.toResponse()
    }

    override fun updateParticipant(id: Long, participantReq: ParticipantReq): ParticipantRes {
        getParticipantById(id)
        val status = getOrElseThrow("Participant Status", participantReq.statusId!!, partStatusRepo::findById)

        val part = Participant(
            id,
            fullName = participantReq.fullName!!,
            shortName = participantReq.shortName!!,
            participantCode = participantReq.participantCode!!,
            bicfiCode = participantReq.bicfiCode!!,
            bankCode = participantReq.bankCode!!,
            phone = participantReq.phone,
            email = participantReq.email,
            address = participantReq.address
        ).apply {
            this.status = status
        }

        val newPart = partRepo.save(part)

        return newPart.toResponse()
    }

    override fun deleteParticipant(id: Long): String {
        getParticipantById(id)

        partRepo.deleteById(id)

        return "The Participant Id[$id] is deleted successfully!!"
    }
}