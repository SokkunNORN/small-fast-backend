package com.sokkun.smallfasttransfer.scheduler

import com.sokkun.smallfasttransfer.service.ITransactionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SettlementScheduler(
    private val service: ITransactionService
) {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(cron = "0 30 9 * * *")
    fun settlementEvent() {
        log.info("Settlement session is starting ...")
        service.settlement()
        log.info("Settlement session has ended!")
    }
}