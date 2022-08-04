package com.sokkun.smallfasttransfer.api.controller

import com.sokkun.smallfasttransfer.api.request.ParticipantUserReq
import com.sokkun.smallfasttransfer.api.response.ParticipantUserRes
import com.sokkun.smallfasttransfer.service.imp.ParticipantUserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/participant/user")
class ParticipantUserController(
    private val service: ParticipantUserService
) {

    @GetMapping
    fun getAllUser(): List<ParticipantUserRes> = service.getAllUser()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: Long): ParticipantUserRes? = service.getUserById(id)

    @PostMapping
    fun createUser(@RequestBody participantUserReq: ParticipantUserReq): ParticipantUserRes? =
        service.createUser(participantUserReq)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable("id") id: Long, @RequestBody participantUserReq: ParticipantUserReq): ParticipantUserRes? =
        service.updateUser(id, participantUserReq)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long): String = service.deleteUser(id)
}