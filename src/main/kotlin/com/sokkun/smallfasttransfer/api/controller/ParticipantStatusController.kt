package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.ParticipantStatusReq
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.model.ParticipantStatus
import com.sokkun.smallfasttransfer.service.imp.ParticipantStatusService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/participant/status")
class ParticipantStatusController(
    private val service: ParticipantStatusService
) {
    @GetMapping
    fun getAllStatus(): ResponseWrapper<List<ParticipantStatus>> = ok(service.getAllStatus())

    @GetMapping("/{id}")
    fun getStatusById(@PathVariable("id") id: Long): ResponseWrapper<ParticipantStatus?> = ok(service.getStatusById(id))

    @PostMapping
    fun createStatus(@RequestBody participantStatusReq: ParticipantStatusReq): ResponseWrapper<ParticipantStatus?> =
        ok(service.createStatus(participantStatusReq))

    @PutMapping("/{id}")
    fun updateStatus(@PathVariable("id") id: Long, @RequestBody participantStatusReq: ParticipantStatusReq):
        ResponseWrapper<ParticipantStatus?> = ok(service.updateStatus(id, participantStatusReq))
}