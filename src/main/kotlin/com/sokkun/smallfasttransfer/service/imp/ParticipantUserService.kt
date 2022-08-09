package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantUserReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes
import com.sokkun.smallfasttransfer.common.Extension.khFormat
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.model.Participant
import com.sokkun.smallfasttransfer.model.ParticipantStatus
import com.sokkun.smallfasttransfer.model.ParticipantUser
import com.sokkun.smallfasttransfer.repository.ParticipantUserRepository
import com.sokkun.smallfasttransfer.service.IParticipantUserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ParticipantUserService(
    private val partUserRepo: ParticipantUserRepository,
    private val partUserStatusService: ParticipantUserStatusService,
    private val partService: ParticipantService
) : IParticipantUserService {
    override fun getAllUser(): List<ParticipantUserRes> = partUserRepo.findAll().map { mapToParticipantUserRes(it) }

    override fun getUserById(id: Long): ParticipantUserRes {
        val user = getOrElseThrow("Participant User", id, partUserRepo::findById)

        return mapToParticipantUserRes(user)
    }

    override fun createUser(participantUserReq: ParticipantUserReq): ParticipantUserRes {

        val user = ParticipantUser(
            0,
            fullName = participantUserReq.fullName,
            username = participantUserReq.username,
            phone = participantUserReq.phone,
            email = participantUserReq.email,
            statusId = participantUserReq.statusId,
            participantId = participantUserReq.participantId
        )

        val newUser = partUserRepo.save(user)

        return mapToParticipantUserRes(newUser)
    }

    override fun updateUser(id: Long, participantUserReq: ParticipantUserReq): ParticipantUserRes {
        getUserById(id)

        val user = ParticipantUser(
            id,
            fullName = participantUserReq.fullName,
            username = participantUserReq.username,
            phone = participantUserReq.phone,
            email = participantUserReq.email,
            statusId = participantUserReq.statusId,
            participantId = participantUserReq.participantId
        )

        val newUser = partUserRepo.save(user)

        return mapToParticipantUserRes(newUser)
    }

    override fun deleteUser(id: Long): String {
        getUserById(id)

        partUserRepo.deleteById(id)

        return "The Participant User Id[$id] is deleted successfully!!"
    }

    private fun mapToParticipantUserRes(partUser: ParticipantUser) : ParticipantUserRes {
        return ParticipantUserRes(
            partUser.id,
            partUser.fullName,
            partUser.username,
            partUser.phone,
            partUser.email,
            status = partUserStatusService.getStatusById(partUser.id),
            participant = partService.getParticipantById(partUser.id),
            partUser.createdAt.khFormat(),
            partUser.updatedAt.khFormat()
        )
    }

}