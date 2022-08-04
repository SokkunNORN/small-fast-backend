package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.common.Extension.khFormat
import com.sokkun.smallfasttransfer.model.Participant
import com.sokkun.smallfasttransfer.model.ParticipantStatus
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.service.IParticipantService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val partRepo: ParticipantRepository,
    private val partStatusService: ParticipantStatusService
) : IParticipantService {
    override fun getAllParticipant(): List<ParticipantRes> = partRepo.findAll().map { mapToParticipantRes(it) }

    override fun getParticipantById(id: Long): ParticipantRes? {
        val part = partRepo.findByIdOrNull(id) ?: return null

        return mapToParticipantRes(part)
    }

    override fun createParticipant(participantReq: ParticipantReq): ParticipantRes? {
        val part = Participant(
            0,
            fullName = participantReq.fullName,
            shortName = participantReq.shortName,
            participantCode = participantReq.participantCode,
            bicfiCode = participantReq.bicfiCode,
            bankCode = participantReq.bankCode,
            phone = participantReq.phone,
            email = participantReq.email,
            address = participantReq.address,
            statusId = participantReq.statusId
        )

        val newPart = partRepo.save(part)

        return mapToParticipantRes(newPart)
    }

    override fun updateParticipant(id: Long, participantReq: ParticipantReq): ParticipantRes? {
        getParticipantById(id) ?: return null

        val part = Participant(
            id,
            fullName = participantReq.fullName,
            shortName = participantReq.shortName,
            participantCode = participantReq.participantCode,
            bicfiCode = participantReq.bicfiCode,
            bankCode = participantReq.bankCode,
            phone = participantReq.phone,
            email = participantReq.email,
            address = participantReq.address,
            statusId = participantReq.statusId
        )

        val newPart = partRepo.save(part)

        return mapToParticipantRes(newPart)
    }

    override fun deleteParticipant(id: Long): String {
        getParticipantById(id) ?: return "ERROR: The Participant Id[$id] does not found!!"

        partRepo.deleteById(id)

        return "The Participant Id[$id] is deleted successfully!!"
    }

    private fun mapToParticipantRes(participant: Participant) : ParticipantRes {
        return ParticipantRes(
            participant.id,
            participant.fullName,
            participant.shortName,
            participant.participantCode,
            participant.bicfiCode,
            participant.bankCode,
            participant.phone,
            participant.email,
            participant.address,
            status = partStatusService.getStatusById(participant.id),
            participant.createdAt.khFormat(),
            participant.updatedAt.khFormat()
        )
    }

}