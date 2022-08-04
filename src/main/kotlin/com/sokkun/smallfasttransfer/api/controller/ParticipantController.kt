package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.ParticipantReq
import com.sokkun.smallfasttransfer.api.response.ParticipantRes
import com.sokkun.smallfasttransfer.service.imp.ParticipantService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/participant")
class ParticipantController(
    private val service: ParticipantService
) {

    @GetMapping
    fun getAllParticipant(): List<ParticipantRes> = service.getAllParticipant()

    @GetMapping("/{id}")
    fun getParticipantById(@PathVariable("id") id: Long): ParticipantRes? = service.getParticipantById(id)

    @PostMapping
    fun createParticipant(@RequestBody participantReq: ParticipantReq): ParticipantRes? = service.createParticipant(participantReq)

    @PutMapping("/{id}")
    fun updateParticipant(@PathVariable("id") id: Long, @RequestBody participantReq: ParticipantReq): ParticipantRes? =
        service.updateParticipant(id, participantReq)

    @DeleteMapping("/{id}")
    fun deleteParticipant(@PathVariable("id") id: Long): String = service.deleteParticipant(id)
}