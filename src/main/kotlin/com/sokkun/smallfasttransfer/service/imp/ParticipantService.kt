package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.common.Extension.khFormat
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.model.Participant
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.service.IParticipantService
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val partRepo: ParticipantRepository,
    private val partStatusService: ParticipantStatusService
) : IParticipantService {
    override fun getAllParticipant(): List<ParticipantRes> = partRepo.findAll().map { mapToParticipantRes(it) }

    override fun getParticipantById(id: Long): ParticipantRes {
        val part = getOrElseThrow("Participant", id, partRepo::findById)

        return mapToParticipantRes(part)
    }

    override fun createParticipant(participantReq: ParticipantReq): ParticipantRes {
        val fullName = getOrElseThrow("fullName", participantReq.fullName)
        val shortName = getOrElseThrow("shortName", participantReq.shortName)
        val partCode = getOrElseThrow("participantCode", participantReq.participantCode)
        val bicfiCode = getOrElseThrow("bicfiCode", participantReq.bicfiCode)
        val bankCode = getOrElseThrow("bankCode", participantReq.bankCode)
        val statusId = getOrElseThrow("statusId", participantReq.statusId)

        val part = Participant(
            0,
            fullName = fullName,
            shortName = shortName,
            participantCode = partCode,
            bicfiCode = bicfiCode,
            bankCode = bankCode,
            phone = participantReq.phone,
            email = participantReq.email,
            address = participantReq.address,
            statusId = statusId
        )

        val newPart = partRepo.save(part)

        return mapToParticipantRes(newPart)
    }

    override fun updateParticipant(id: Long, participantReq: ParticipantReq): ParticipantRes {
        getParticipantById(id)
        val fullName = getOrElseThrow("fullName", participantReq.fullName)
        val shortName = getOrElseThrow("shortName", participantReq.shortName)
        val partCode = getOrElseThrow("participantCode", participantReq.participantCode)
        val bicfiCode = getOrElseThrow("bicfiCode", participantReq.bicfiCode)
        val bankCode = getOrElseThrow("bankCode", participantReq.bankCode)
        val statusId = getOrElseThrow("statusId", participantReq.statusId)

        val part = Participant(
            id,
            fullName = fullName,
            shortName = shortName,
            participantCode = partCode,
            bicfiCode = bicfiCode,
            bankCode = bankCode,
            phone = participantReq.phone,
            email = participantReq.email,
            address = participantReq.address,
            statusId = statusId
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