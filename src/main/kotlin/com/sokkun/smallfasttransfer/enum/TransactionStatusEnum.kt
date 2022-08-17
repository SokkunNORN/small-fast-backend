package com.sokkun.smallfasttransfer.enum

enum class TransactionStatusEnum(val id: Long, val code: String?) {
    PENDING(id = 1, code = "PEND"),
    SIGNED_AND_SENT(id = 2, code = "SENT"),
    SETTLED(id = 3, code = "SETTLED"),
    UNSETTLED(id = 4, code = "UNSETTLED");

    companion object {
        val VALID_HISTORY = listOf(SETTLED, UNSETTLED).map { it.id }
        val VALID_LOG = listOf(PENDING, SIGNED_AND_SENT, SETTLED, UNSETTLED).map { it.id }
    }
}