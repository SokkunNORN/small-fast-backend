package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.TransactionStatus
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionStatusRepository: JpaRepository<TransactionStatus, Long>