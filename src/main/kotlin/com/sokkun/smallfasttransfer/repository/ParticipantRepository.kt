package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.model.Participant
import org.springframework.data.jpa.repository.JpaRepository

interface ParticipantRepository: JpaRepository<Participant, Long>