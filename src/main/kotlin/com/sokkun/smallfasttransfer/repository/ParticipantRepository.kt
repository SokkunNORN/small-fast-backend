package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.Participant
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface ParticipantRepository: PagingAndSortingRepository<Participant, Long>, JpaSpecificationExecutor<Participant>