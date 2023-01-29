package com.example.kw.service

import com.example.kw.dto.payment.ListPaymentsRequestDto
import com.example.kw.dto.payment.ListPaymentsResponseDto
import com.example.kw.dto.payment.PaymentRequestDto
import com.example.kw.dto.payment.PaymentResponseDto
import com.example.kw.repository.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {

    suspend fun createPayment(requestDto: PaymentRequestDto): PaymentResponseDto =
        withContext(Dispatchers.IO) {
            require(requestDto.paymentMethod.priceModifierRange.contains(requestDto.priceModifier)) {
                "Price modifier out of range!"
            }

            PaymentResponseDto(
                finalPrice = requestDto.price * requestDto.priceModifier,
                points = (requestDto.price * requestDto.paymentMethod.pointsRate).roundToInt(),
            )
//                .also { paymentResponseDto ->
//                paymentRepository.save(Payment.of(requestDto, paymentResponseDto))
//            }
        }

    suspend fun listPayments(requestDto: ListPaymentsRequestDto): ListPaymentsResponseDto =
        withContext(Dispatchers.IO) {
            paymentRepository.findByDatetimeBetween(
                from = requestDto.startDateTime,
                to = requestDto.endDateTime,
            ).map { payment ->
                ListPaymentsResponseDto.SalePaymentDto.of(payment)
            }.let { salePayments ->
                ListPaymentsResponseDto(sales = salePayments)
            }
        }
}
