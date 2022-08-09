package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantUserReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes
import com.sokkun.smallfasttransfer.common.Extension.khFormat
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.model.Participant
import com.sokkun.smallfasttransfer.model.ParticipantStatus
import com.sokkun.smallfasttransfer.model.ParticipantUser
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.repository.ParticipantUserRepository
import com.sokkun.smallfasttransfer.repository.ParticipantUserStatusRepository
import com.sokkun.smallfasttransfer.service.IParticipantUserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ParticipantUserService(
    private val partUserRepo: ParticipantUserRepository,
    private val partUserStatusRepo: ParticipantUserStatusRepository,
    private val partRepo: ParticipantRepository,
    private val partService: ParticipantService
) : IParticipantUserService {
    override fun getAllUser(): List<ParticipantUserRes> = partUserRepo.findAll().map { mapToParticipantUserRes(it) }

    override fun getUserById(id: Long): ParticipantUserRes {
        val user = getOrElseThrow("Participant User", id, partUserRepo::findById)

        return mapToParticipantUserRes(user)
    }

    override fun createUser(participantUserReq: ParticipantUserReq): ParticipantUserRes {
        val fullName = getOrElseThrow("fullName", participantUserReq.fullName)
        val username = getOrElseThrow("username", participantUserReq.username)
        val statusId = getOrElseThrow("statusId", participantUserReq.statusId)
        val participantId = getOrElseThrow("participantId", participantUserReq.participantId)
        getOrElseThrow("Participant User Status id", statusId, partUserStatusRepo::findById)
        getOrElseThrow("Participant id", participantId, partRepo::findById)

        val user = ParticipantUser(
            0,
            fullName = fullName,
            username = username,
            phone = participantUserReq.phone,
            email = participantUserReq.email,
            statusId = statusId,
            participantId = participantId
        )

        val newUser = partUserRepo.save(user)

        return mapToParticipantUserRes(newUser)
    }

    override fun updateUser(id: Long, participantUserReq: ParticipantUserReq): ParticipantUserRes {
        getUserById(id)
        val fullName = getOrElseThrow("fullName", participantUserReq.fullName)
        val username = getOrElseThrow("username", participantUserReq.username)
        val statusId = getOrElseThrow("statusId", participantUserReq.statusId)
        val participantId = getOrElseThrow("participantId", participantUserReq.participantId)
        getOrElseThrow("Participant Status", statusId, partUserStatusRepo::findById)
        getOrElseThrow("Participant User Status", statusId, partRepo::findById)

        val user = ParticipantUser(
            id,
            fullName = fullName,
            username = username,
            phone = participantUserReq.phone,
            email = participantUserReq.email,
            statusId = statusId,
            participantId = participantId
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
        val status = partUserStatusRepo.findByIdOrNull(partUser.statusId)
        val participant = partService.mapToParticipantRes(partRepo.findByIdOrNull(partUser.participantId)!!)
        return ParticipantUserRes(
            partUser.id,
            partUser.fullName,
            partUser.username,
            partUser.phone,
            partUser.email,
            status = status,
            participant = participant,
            partUser.createdAt.khFormat(),
            partUser.updatedAt.khFormat()
        )
    }

}