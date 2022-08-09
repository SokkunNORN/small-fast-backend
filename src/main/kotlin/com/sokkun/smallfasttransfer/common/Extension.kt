package com.sokkun.smallfasttransfer.common

import com.sokkun.smallfasttransfer.api.exception.IdNotFoundException
import com.sokkun.smallfasttransfer.api.exception.ParamNotFoundException
import com.sokkun.smallfasttransfer.api.exception.RecordExistException
import com.sokkun.smallfasttransfer.api.response.helper.PageResponse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Extension {
    fun LocalDate.khFormat () : String {
        return this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }

    fun LocalDateTime.khFormat () : String {
        return this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
    }
}

infix fun Boolean.then(action: () -> Unit): Boolean {
    if (this)
        action.invoke()
    return this
}

fun <T> getOrElseThrow(name: String, id: Long, loader: (Long) -> Optional<T>): T {
    return loader(id).orElseThrow { throw IdNotFoundException(name, id) }
}

fun <T> getOrElseThrow(name: String, field: String, loader: (String) -> Optional<T>): T {
    return loader(field).orElseThrow { throw ParamNotFoundException("$name[$field]") }
}

fun <T> getOrElseThrow(name: String, field: T?): T {
    return field ?: throw ParamNotFoundException("The param[$name]")
}

fun <T> Optional<T>.orElseThrow(msg: String): T {
    return this.orElseThrow { throw ParamNotFoundException(msg) }
}

fun Boolean.existsAndThrow(msg: String): Boolean {
    this.then { throw RecordExistException(msg) }
    return this
}

fun <T> toPageResponse() {  }