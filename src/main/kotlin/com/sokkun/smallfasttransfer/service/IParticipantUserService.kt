package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.ParticipantUserReq
import com.sokkun.smallfasttransfer.api.request.filter.ParticipantUserFilterRes
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import org.springframework.data.domain.Pageable

interface IParticipantUserService {
    fun getAllUser(filterRes: ParticipantUserFilterRes?, pageable: Pageable): PageResponse<ParticipantUserRes>
    fun getUserById(id: Long): ParticipantUserRes
    fun createUser(participantUserReq: ParticipantUserReq): ParticipantUserRes
    fun updateUser(id: Long, participantUserReq: ParticipantUserReq): ParticipantUserRes
    fun deleteUser(id: Long): String
}