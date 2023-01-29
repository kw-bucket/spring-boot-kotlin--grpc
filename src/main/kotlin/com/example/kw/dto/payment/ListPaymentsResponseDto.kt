package com.example.kw.dto.payment

import com.example.kw.entity.Payment
import com.example.kw.grpc.Payment.ListPaymentsGrpcResponse.Sales
import java.math.BigDecimal
import java.time.LocalDateTime

data class ListPaymentsResponseDto(
    val sales: List<SalePaymentDto>,
) {
    data class SalePaymentDto(
        val datetime: LocalDateTime,
        val sales: BigDecimal,
        val points: Int,
    ) {
        companion object {
            fun of(payment: Payment): SalePaymentDto = SalePaymentDto(
                datetime = payment.datetime,
                sales = payment.sales,
                points = payment.points,
            )
        }
    }
}

fun ListPaymentsResponseDto.SalePaymentDto.toProto(): Sales =
    Sales.newBuilder()
        .setDatetime(this.datetime.toString())
        .setSales(this.sales.toDouble())
        .setPoints(this.points)
        .build()
