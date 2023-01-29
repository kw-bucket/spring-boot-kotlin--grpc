package com.example.kw.entity

import com.example.kw.dto.payment.PaymentRequestDto
import com.example.kw.dto.payment.PaymentResponseDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Table(name = "payment")
data class Payment(
    @Id
    val id: Long = 0,
    val datetime: LocalDateTime,
    val customerId: String,
    val sales: BigDecimal,
    val points: Int,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun of(request: PaymentRequestDto, response: PaymentResponseDto) = Payment(
            datetime = request.datetime,
            customerId = request.customerId,
            sales = response.finalPrice.toBigDecimal(),
            points = response.points,
        )
    }
}
