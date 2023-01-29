package com.example.kw.dto.error

data class ErrorResponseDto(
    val status: Int,
    val message: String,
    val description: String? = null,
)
