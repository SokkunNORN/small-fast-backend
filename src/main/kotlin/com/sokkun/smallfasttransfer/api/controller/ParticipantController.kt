package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.api.response.helper.ResponseWrapper
import com.sokkun.smallfasttransfer.api.response.helper.ok
import com.sokkun.smallfasttransfer.service.imp.ParticipantService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.SortDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/participant")
class ParticipantController(
    private val service: ParticipantService
) {

    @GetMapping
    fun getAllParticipant(
        req: RequestParam?,
        @SortDefault(sort = ["createdAt"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseWrapper<List<ParticipantRes>> = ok(service.getAllParticipant())

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