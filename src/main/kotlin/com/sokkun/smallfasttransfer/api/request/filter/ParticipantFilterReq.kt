package com.sokkun.smallfasttransfer.api.request.filter

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class ParticipantFilterReq(
    val q: String?,
    val statusId: Long?,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) val dateFrom: LocalDate?,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) val dateTo: LocalDate?
)