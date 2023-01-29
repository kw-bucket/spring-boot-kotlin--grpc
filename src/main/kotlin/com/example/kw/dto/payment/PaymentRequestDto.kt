package com.example.kw.dto.payment

import java.time.LocalDateTime

data class PaymentRequestDto(
    val customerId: String,
    val price: Double,
    val priceModifier: Double,
    val paymentMethod: PaymentMethod,
    val datetime: LocalDateTime,
    val additionalItem: AdditionalItem? = null,
) {
    data class AdditionalItem(
        val last4: String? = null,
        val courier: Courier? = null,
    )
}

enum class Courier {
    YAMATO, SAGAWA,
}

enum class PaymentMethod(
    val priceModifierRange: ClosedFloatingPointRange<Double>,
    val pointsRate: Double,
) {
    CASH(priceModifierRange = (0.9..1.0), pointsRate = 0.05),
    CASH_ON_DELIVERY(priceModifierRange = (1.0..1.02), pointsRate = 0.05),
    VISA(priceModifierRange = (0.95..1.0), pointsRate = 0.03),
    MASTERCARD(priceModifierRange = (0.95..1.0), pointsRate = 0.03),
    AMEX(priceModifierRange = (0.98..1.01), pointsRate = 0.02),
    JCB(priceModifierRange = (0.95..1.0), pointsRate = 0.05),
    LINE_PAY(priceModifierRange = (1.0..1.0), pointsRate = 0.01),
    PAYPAY(priceModifierRange = (1.0..1.0), pointsRate = 0.01),
    POINTS(priceModifierRange = (1.0..1.0), pointsRate = 0.0),
    GRAB_PAY(priceModifierRange = (1.0..1.0), pointsRate = 0.01),
    BANK_TRANSFER(priceModifierRange = (1.0..1.0), pointsRate = 0.0),
    CHEQUE(priceModifierRange = (0.9..1.0), pointsRate = 0.0),
}
