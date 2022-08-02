package com.sokkun.smallfasttransfer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(LiquibaseProperties::class)
class SmallFastTransferApplication

fun main(args: Array<String>) {
	runApplication<SmallFastTransferApplication>(*args)
}
