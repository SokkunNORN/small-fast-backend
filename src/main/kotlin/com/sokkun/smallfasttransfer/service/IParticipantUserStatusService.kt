package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.ParticipantUserStatusReq
import com.sokkun.smallfasttransfer.domain.model.ParticipantUserStatus

interface IParticipantUserStatusService {
    fun getAllStatus(): List<ParticipantUserStatus>
    fun getStatusById(id: Long): ParticipantUserStatus
    fun createStatus(statusReq: ParticipantUserStatusReq): ParticipantUserStatus
    fun updateStatus(id: Long, statusReq: ParticipantUserStatusReq): ParticipantUserStatus
}