package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.ParticipantUserStatusReq
import com.sokkun.smallfasttransfer.model.ParticipantUserStatus
import com.sokkun.smallfasttransfer.service.imp.ParticipantUserStatusService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/participant/user/status")
class ParticipantUserStatusController(
    private val service: ParticipantUserStatusService
) {
    @GetMapping
    fun getAllStatus(): List<ParticipantUserStatus> = service.getAllStatus()

    @GetMapping("/{id}")
    fun getStatusById(@PathVariable("id") id: Long): ParticipantUserStatus? = service.getStatusById(id)

    @PostMapping
    fun createStatus(@RequestBody participantUserStatusReq: ParticipantUserStatusReq): ParticipantUserStatus? =
        service.createStatus(participantUserStatusReq)

    @PutMapping("/{id}")
    fun updateStatus(
        @PathVariable("id") id: Long,
        @RequestBody participantUserStatusReq: ParticipantUserStatusReq
    ): ParticipantUserStatus? = service.updateStatus(id, participantUserStatusReq)
}