package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.ParticipantStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantStatusRepository: JpaRepository<ParticipantStatus, Long>