package com.sokkun.smallfasttransfer.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Extension {
    fun LocalDate.khFormat () : String {
        return this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }

    fun LocalDateTime.khFormat () : String {
        return this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
    }
}