package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.filter.ParticipantFilterReq
import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.service.IParticipantService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/participant")
class ParticipantController(
    private val service: IParticipantService
) {

    @GetMapping
    fun getAllParticipant(filterReq: ParticipantFilterReq, pageable: Pageable):
        ResponseWrapper<PageResponse<ParticipantRes>> = ok(service.getAllParticipant(filterReq, pageable))

    @GetMapping("/{id}")
    fun getParticipantById(@PathVariable("id") id: Long): ResponseWrapper<ParticipantRes> = ok(service.getParticipantById(id))

    @PostMapping
    fun createParticipant(@RequestBody participantReq: ParticipantReq):
        ResponseWrapper<ParticipantRes> = ok(service.createParticipant(participantReq))

    @PutMapping("/{id}")
    fun updateParticipant(@PathVariable("id") id: Long, @RequestBody participantReq: ParticipantReq):
        ResponseWrapper<ParticipantRes> = ok(service.updateParticipant(id, participantReq))

    @DeleteMapping("/{id}")
    fun deleteParticipant(@PathVariable("id") id: Long): ResponseWrapper<String> = ok(service.deleteParticipant(id))
}