package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.Balance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface BalanceRepository: JpaRepository<Balance, Long>, JpaSpecificationExecutor<Balance> {
    @Query("SELECT * FROM balance", nativeQuery = true)
    fun getAll(): List<Balance>
}