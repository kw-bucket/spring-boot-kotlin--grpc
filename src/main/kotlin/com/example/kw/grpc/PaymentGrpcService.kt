package com.example.kw.grpc

import com.example.kw.dto.payment.PaymentRequestDto
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

    override suspend fun createPayment(request: PaymentGrpcRequest): PaymentGrpcResponse {
        return paymentService.createPayment(requestDto = PaymentRequestDto.of(request)).let {
            PaymentGrpcResponse.newBuilder()
                .setFinalPrice(it.finalPrice.toString())
                .setPoints(it.points)
                .build()
        }
    }
}
