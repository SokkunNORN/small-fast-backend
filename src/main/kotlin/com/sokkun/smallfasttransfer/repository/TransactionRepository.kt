package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

interface TransactionRepository:
    PagingAndSortingRepository<Transaction, Long>,
    JpaSpecificationExecutor<Transaction>,
    JpaRepository<Transaction, Long>
{
    @Query(value = "SELECT t FROM Transaction t WHERE t.status.id = 2 ORDER BY t.sentAt ASC")
    fun getSentTransactions(): MutableList<Transaction>
}