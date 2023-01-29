package com.example.kw.repository

import com.example.kw.entity.Payment
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository : CoroutineSortingRepository<Payment, Long>
