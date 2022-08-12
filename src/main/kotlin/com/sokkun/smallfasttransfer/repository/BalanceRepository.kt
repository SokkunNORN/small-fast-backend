package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.Balance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BalanceRepository: JpaRepository<Balance, Long>, JpaSpecificationExecutor<Balance>