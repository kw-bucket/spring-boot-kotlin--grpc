package com.example.kw.repository

import com.example.kw.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PaymentRepository : JpaRepository<Payment, Long> {

    fun findByDatetimeBetween(from: LocalDateTime, to: LocalDateTime): List<Payment>
}
