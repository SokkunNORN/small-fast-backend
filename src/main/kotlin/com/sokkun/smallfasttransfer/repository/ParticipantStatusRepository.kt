package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.model.ParticipantStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantStatusRepository: JpaRepository<ParticipantStatus, Long>