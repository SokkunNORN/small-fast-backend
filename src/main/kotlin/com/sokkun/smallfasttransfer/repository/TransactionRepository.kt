package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.Transaction
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository

interface TransactionRepository: PagingAndSortingRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction>