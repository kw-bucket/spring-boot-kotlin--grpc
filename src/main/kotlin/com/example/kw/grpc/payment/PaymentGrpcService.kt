package com.example.kw.grpc.payment

import com.example.kw.grpc.payment.Payment.PaymentRequest
import com.example.kw.grpc.payment.Payment.PaymentResponse
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class PaymentGrpcService : PaymentServiceGrpcKt.PaymentServiceCoroutineImplBase() {

    override suspend fun createPayment(request: PaymentRequest): PaymentResponse {
        return PaymentResponse.newBuilder()
            .setFinalPrice("???")
            .setPoints(8)
            .build()
    }
}
