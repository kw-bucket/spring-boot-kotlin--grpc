package com.example.kw.service

import com.example.kw.dto.payment.PaymentRequestDto
import com.example.kw.dto.payment.PaymentResponseDto
import com.example.kw.entity.Payment
import com.example.kw.repository.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    suspend fun createPayment(requestDto: PaymentRequestDto): PaymentResponseDto = withContext(Dispatchers.IO) {
        require(requestDto.paymentMethod.priceModifierRange.contains(requestDto.priceModifier)) {
            "Price modifier out of range!"
        }

        PaymentResponseDto(
            finalPrice = requestDto.price * requestDto.priceModifier,
            points = (requestDto.price * requestDto.paymentMethod.pointsRate).roundToInt(),
        ).also { paymentResponseDto ->
            logger.info("{}", Payment.of(requestDto, paymentResponseDto))

            paymentRepository.save(Payment.of(requestDto, paymentResponseDto))
        }
    }
}
