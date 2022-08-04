package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.request.ParticipantStatusReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.model.ParticipantStatus
import com.sokkun.smallfasttransfer.service.imp.ParticipantStatusService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/participant/status")
class ParticipantStatusController(
    private val service: ParticipantStatusService
) {
    @GetMapping
    fun getAllStatus(): List<ParticipantStatus> = service.getAllStatus()

    @GetMapping("/{id}")
    fun getStatusById(@PathVariable("id") id: Long): ParticipantStatus? = service.getStatusById(id)

    @PostMapping
    fun createStatus(@RequestBody participantStatusReq: ParticipantStatusReq): ParticipantStatus? =
        service.createStatus(participantStatusReq)

    @PutMapping("/{id}")
    fun updateStatus(@PathVariable("id") id: Long, @RequestBody participantStatusReq: ParticipantStatusReq): ParticipantStatus? =
        service.updateStatus(id, participantStatusReq)
}