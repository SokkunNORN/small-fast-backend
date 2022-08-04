package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.model.ParticipantUser
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantUserRepository: JpaRepository<ParticipantUser, Long>