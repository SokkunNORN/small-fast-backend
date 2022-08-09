package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantStatusReq
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.model.ParticipantStatus
import com.sokkun.smallfasttransfer.repository.ParticipantStatusRepository
import com.sokkun.smallfasttransfer.service.IParticipantStatusService
import org.springframework.stereotype.Service

@Service
class ParticipantStatusService(
    private val partiStatusRepo: ParticipantStatusRepository
) : IParticipantStatusService {
    override fun getAllStatus(): List<ParticipantStatus> = partiStatusRepo.findAll()

    override fun getStatusById(id: Long): ParticipantStatus =
        getOrElseThrow("Participant Status", id, partiStatusRepo::findById)

    override fun createStatus(statusReq: ParticipantStatusReq): ParticipantStatus {
        val name = getOrElseThrow("name", statusReq.name)
        val description = getOrElseThrow("description", statusReq.description)

        val status = ParticipantStatus(0, name, description)

        return partiStatusRepo.save(status)
    }

    override fun updateStatus(id: Long, statusReq: ParticipantStatusReq): ParticipantStatus {
        getStatusById(id)
        val name = getOrElseThrow("name", statusReq.name)
        val description = getOrElseThrow("description", statusReq.description)

        val status = ParticipantStatus(id, name, description)

        return partiStatusRepo.save(status)
    }
}