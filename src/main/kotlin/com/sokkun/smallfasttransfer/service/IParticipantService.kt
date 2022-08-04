package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes

interface IParticipantService {
    fun getAllParticipant(): List<ParticipantRes>
    fun getParticipantById(id: Long): ParticipantRes?
    fun createParticipant(participantReq: ParticipantReq): ParticipantRes?
    fun updateParticipant(id: Long, participantReq: ParticipantReq): ParticipantRes?
    fun deleteParticipant(id: Long): String
}