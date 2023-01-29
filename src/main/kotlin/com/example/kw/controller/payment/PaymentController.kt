package com.example.kw.controller.payment

import com.example.kw.dto.payment.PaymentRequestDto
import com.example.kw.dto.payment.PaymentResponseDto
import com.example.kw.service.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/payments")
class PaymentController(
    private val paymentService: PaymentService,
) {

    @PostMapping
    suspend fun createPayment(@RequestBody @Valid requestDto: PaymentRequestDto): ResponseEntity<PaymentResponseDto> =
        ResponseEntity.ok().body(paymentService.createPayment(requestDto = requestDto))
}
