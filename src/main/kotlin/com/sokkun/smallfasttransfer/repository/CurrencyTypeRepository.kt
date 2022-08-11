package com.sokkun.smallfasttransfer.repository

import com.sokkun.smallfasttransfer.domain.model.CurrencyType
import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyTypeRepository: JpaRepository<CurrencyType, Long>