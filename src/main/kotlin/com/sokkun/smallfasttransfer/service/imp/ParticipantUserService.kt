package com.sokkun.smallfasttransfer.service.imp

import com.sokkun.smallfasttransfer.api.request.ParticipantUserReq
import com.sokkun.smallfasttransfer.api.request.filter.ParticipantUserFilterRes
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import com.sokkun.smallfasttransfer.common.checkingSortFields
import com.sokkun.smallfasttransfer.common.getOrElseThrow
import com.sokkun.smallfasttransfer.common.toPageResponse
import com.sokkun.smallfasttransfer.model.ParticipantUser
import com.sokkun.smallfasttransfer.repository.ParticipantRepository
import com.sokkun.smallfasttransfer.repository.ParticipantUserRepository
import com.sokkun.smallfasttransfer.repository.ParticipantUserStatusRepository
import com.sokkun.smallfasttransfer.service.IParticipantUserService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ParticipantUserService(
    private val partUserRepo: ParticipantUserRepository,
    private val partUserStatusRepo: ParticipantUserStatusRepository,
    private val partRepo: ParticipantRepository
) : IParticipantUserService {
    override fun getAllUser(
        filterRes: ParticipantUserFilterRes?,
        pageable: Pageable
    ): PageResponse<ParticipantUserRes> {
        pageable.checkingSortFields(ParticipantUser::class.java)
        val all = partUserRepo.findAll(pageable)

        return all.map { it.toResponse() }.toPageResponse()
    }

    override fun getUserById(id: Long): ParticipantUserRes {
        val user = getOrElseThrow("Participant User", id, partUserRepo::findById)

        return user.toResponse()
    }

    override fun createUser(participantUserReq: ParticipantUserReq): ParticipantUserRes {
        val status = getOrElseThrow("Participant User Status id", participantUserReq.statusId, partUserStatusRepo::findById)
        val participant = getOrElseThrow("Participant id", participantUserReq.participantId, partRepo::findById)

        val user = ParticipantUser(
            0,
            fullName = participantUserReq.fullName,
            username = participantUserReq.username,
            phone = participantUserReq.phone,
            email = participantUserReq.email
        ).apply {
            this.status = status
            this.participant = participant
        }

        val newUser = partUserRepo.save(user)

        return newUser.toResponse()
    }

    override fun updateUser(id: Long, participantUserReq: ParticipantUserReq): ParticipantUserRes {
        getUserById(id)
        val status = getOrElseThrow("Participant User Status id", participantUserReq.statusId, partUserStatusRepo::findById)
        val participant = getOrElseThrow("Participant id", participantUserReq.participantId, partRepo::findById)

        val user = ParticipantUser(
            id,
            fullName = participantUserReq.fullName,
            username = participantUserReq.username,
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