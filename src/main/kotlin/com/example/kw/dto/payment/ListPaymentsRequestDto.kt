package com.example.kw.dto.payment

import com.example.kw.grpc.Payment.ListPaymentsGrpcRequest
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ListPaymentsRequestDto(
    @JsonFormat(pattern = datTimeFormat)
    val startDateTime: LocalDateTime,
    @JsonFormat(pattern = datTimeFormat)
    val endDateTime: LocalDateTime,
) {
    companion object {
        const val datTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        fun of(request: ListPaymentsGrpcRequest) = ListPaymentsRequestDto(
            startDateTime = LocalDateTime.parse(
                request.startDateTime,
                DateTimeFormatter.ofPattern(datTimeFormat),
            ),
            endDateTime = LocalDateTime.parse(
                request.endDateTime,
                DateTimeFormatter.ofPattern(datTimeFormat),
            ),
        )
    }
}
