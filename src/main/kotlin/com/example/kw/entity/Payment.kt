package com.example.kw.entity

import com.example.kw.dto.payment.PaymentRequestDto
import com.example.kw.dto.payment.PaymentResponseDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "payment", schema = "example_grpc")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
