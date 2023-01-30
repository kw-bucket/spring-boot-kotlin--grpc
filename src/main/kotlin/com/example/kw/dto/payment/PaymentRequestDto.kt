package com.example.kw.dto.payment

import com.example.kw.grpc.Payment.PaymentGrpcRequest
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class PaymentRequestDto(
    val customerId: String,
    val price: Double,
    val priceModifier: Double,
    val paymentMethod: PaymentMethod,
    @JsonFormat(pattern = datTimeFormat)
    val datetime: LocalDateTime,
    val additionalItem: AdditionalItem? = null,
) {

    data class AdditionalItem(
        val last4: String? = null,
        val courier: Courier? = null,
    )

    companion object {
        const val datTimeFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        fun of(request: PaymentGrpcRequest) = PaymentRequestDto(
            customerId = request.customerId,
            price = request.price.toDouble(),
            priceModifier = request.priceModifier,
            paymentMethod = PaymentMethod.valueOf(request.paymentMethod),
            datetime = LocalDateTime.parse(
                request.datetime,
                DateTimeFormatter.ofPattern(datTimeFormat),
            ),
            additionalItem = request.takeIf { it.hasAdditionalItem() }?.let { req ->
                AdditionalItem(
                    last4 = req.additionalItem.takeIf { it.hasLast4() }?.last4,
                    courier = req.additionalItem.takeIf { it.hasCourier() }?.let { Courier.valueOf(it.courier) },
                )
            },
        )
    }

    fun validate() {
        require(paymentMethod.priceModifierRange.contains(priceModifier)) { "Price modifier out of range!" }

        if (paymentMethod.type == PaymentType.CARD) {
            check(value = additionalItem?.last4?.matches(regex = "\\d{4}".toRegex()) ?: false) {
                "last4 must be 4 digits!"
            }
        }
    }
}

enum class Courier {
    YAMATO, SAGAWA,
}

enum class PaymentType {
    CASH, CARD, CASHLESS,
}
enum class PaymentMethod(
    val priceModifierRange: ClosedFloatingPointRange<Double>,
    val pointsRate: Double,
    val type: PaymentType,
) {
    CASH(priceModifierRange = (0.9..1.0), pointsRate = 0.05, type = PaymentType.CASH),
    CASH_ON_DELIVERY(priceModifierRange = (1.0..1.02), pointsRate = 0.05, type = PaymentType.CASH),
    VISA(priceModifierRange = (0.95..1.0), pointsRate = 0.03, type = PaymentType.CARD),
    MASTERCARD(priceModifierRange = (0.95..1.0), pointsRate = 0.03, type = PaymentType.CARD),
    AMEX(priceModifierRange = (0.98..1.01), pointsRate = 0.02, type = PaymentType.CARD),
    JCB(priceModifierRange = (0.95..1.0), pointsRate = 0.05, type = PaymentType.CARD),
    LINE_PAY(priceModifierRange = (1.0..1.0), pointsRate = 0.01, type = PaymentType.CASHLESS),
    PAYPAY(priceModifierRange = (1.0..1.0), pointsRate = 0.01, type = PaymentType.CASHLESS),
    POINTS(priceModifierRange = (1.0..1.0), pointsRate = 0.0, type = PaymentType.CASHLESS),
    GRAB_PAY(priceModifierRange = (1.0..1.0), pointsRate = 0.01, type = PaymentType.CASHLESS),
    BANK_TRANSFER(priceModifierRange = (1.0..1.0), pointsRate = 0.0, type = PaymentType.CASHLESS),
    CHEQUE(priceModifierRange = (0.9..1.0), pointsRate = 0.0, type = PaymentType.CASHLESS),
}
