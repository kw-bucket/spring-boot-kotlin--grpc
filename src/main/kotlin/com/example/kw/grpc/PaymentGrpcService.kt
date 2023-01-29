package com.example.kw.grpc

import com.example.kw.dto.payment.ListPaymentsRequestDto
import com.example.kw.dto.payment.PaymentRequestDto
import com.example.kw.dto.payment.toProto
import com.example.kw.grpc.Payment.ListPaymentsGrpcRequest
import com.example.kw.grpc.Payment.ListPaymentsGrpcResponse
import com.example.kw.grpc.Payment.PaymentGrpcRequest
import com.example.kw.grpc.Payment.PaymentGrpcResponse
import com.example.kw.service.PaymentService
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@GrpcService
class PaymentGrpcService(
    private val paymentService: PaymentService,
) : PaymentServiceGrpcKt.PaymentServiceCoroutineImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun createPayment(request: PaymentGrpcRequest): PaymentGrpcResponse =
        paymentService.createPayment(requestDto = PaymentRequestDto.of(request)).let {
            PaymentGrpcResponse.newBuilder()
                .setFinalPrice(it.finalPrice.toString())
                .setPoints(it.points)
                .build()
        }

    override suspend fun listPayments(request: ListPaymentsGrpcRequest): ListPaymentsGrpcResponse {
        return paymentService.listPayments(requestDto = ListPaymentsRequestDto.of(request)).let { listPaymentsDto ->
            ListPaymentsGrpcResponse.newBuilder()
                .addAllSales(listPaymentsDto.sales.map { it.toProto() })
                .build()
        }
    }
}
