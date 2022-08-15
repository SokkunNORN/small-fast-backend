package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.CustomerAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CustomerAccountRepository: JpaRepository<CustomerAccount, Long>, JpaSpecificationExecutor<CustomerAccount>