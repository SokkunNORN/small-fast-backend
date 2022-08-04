package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.model.ParticipantUserStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantUserStatusRepository: JpaRepository<ParticipantUserStatus, Long>