package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.ParticipantUserReq
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes

interface IParticipantUserService {
    fun getAllUser(): List<ParticipantUserRes>
    fun getUserById(id: Long): ParticipantUserRes
    fun createUser(participantUserReq: ParticipantUserReq): ParticipantUserRes
    fun updateUser(id: Long, participantUserReq: ParticipantUserReq): ParticipantUserRes
    fun deleteUser(id: Long): String
}