package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.ParticipantStatusReq
import com.sokkun.smallfasttransfer.model.ParticipantStatus

interface IParticipantStatusService {
    fun getAllStatus(): List<ParticipantStatus>
    fun getStatusById(id: Long): ParticipantStatus
    fun createStatus(statusReq: ParticipantStatusReq): ParticipantStatus
    fun updateStatus(id: Long, statusReq: ParticipantStatusReq): ParticipantStatus
}