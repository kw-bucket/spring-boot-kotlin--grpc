package com.example.kw.dto.payment

import java.time.LocalDateTime

data class ListPaymentsRequestDto(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
)
