package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantUserStatusReq
import com.sokkun.smallfasttransfer.model.ParticipantUserStatus
import com.sokkun.smallfasttransfer.repository.ParticipantUserStatusRepository
import com.sokkun.smallfasttransfer.service.IParticipantUserStatusService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ParticipantUserStatusService(
    private val userStatusRepo: ParticipantUserStatusRepository
) : IParticipantUserStatusService {
    override fun getAllStatus(): List<ParticipantUserStatus> = userStatusRepo.findAll()

    override fun getStatusById(id: Long): ParticipantUserStatus? = userStatusRepo.findByIdOrNull(id)

    override fun createStatus(statusReq: ParticipantUserStatusReq): ParticipantUserStatus? {
        val status = ParticipantUserStatus(0, statusReq.name, statusReq.description)

        return userStatusRepo.save(status)
    }

    override fun updateStatus(id: Long, statusReq: ParticipantUserStatusReq): ParticipantUserStatus? {
        getStatusById(id) ?: return null

        val status = ParticipantUserStatus(id, statusReq.name, statusReq.description)

        return userStatusRepo.save(status)
    }
}