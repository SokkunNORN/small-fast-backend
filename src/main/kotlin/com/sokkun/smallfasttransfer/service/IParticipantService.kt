package com.sokkun.smallfasttransfer.service

import com.sokkun.smallfasttransfer.api.request.filter.ParticipantFilterReq
import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import org.springframework.data.domain.Pageable

interface IParticipantService {
    fun getAllParticipant(filterReq: ParticipantFilterReq?, pageable: Pageable): PageResponse<ParticipantRes>
    fun getParticipantById(id: Long): ParticipantRes
    fun createParticipant(participantReq: ParticipantReq): ParticipantRes
    fun updateParticipant(id: Long, participantReq: ParticipantReq): ParticipantRes
    fun deleteParticipant(id: Long): String
}