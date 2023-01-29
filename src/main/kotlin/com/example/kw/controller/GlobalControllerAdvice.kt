package com.example.kw.controller

import com.example.kw.dto.error.ErrorResponseDto
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class GlobalControllerAdvisor {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponseDto> =
        buildResponse(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            description = ex.message,
        ).also {
            logger.error("Exception: {}", ex.stackTraceToString())
        }

    @ExceptionHandler(
        value = [
            HttpMessageNotReadableException::class,
            MethodArgumentNotValidException::class,
            MissingServletRequestParameterException::class,
            MethodArgumentTypeMismatchException::class,
            InvalidFormatException::class,
            IllegalArgumentException::class,
            IllegalStateException::class,
        ],
    )
    fun handleInvalidRequest(ex: Exception): ResponseEntity<ErrorResponseDto> {
        val description: String? = when (ex) {
            is HttpMessageNotReadableException -> when (val cause = ex.cause) {
                is InvalidFormatException -> "${cause.path.last().fieldName} is missing or invalid"
                is MissingKotlinParameterException -> "${cause.path.last().fieldName} is missing or invalid"
                else -> ex.message
            }
            is IllegalArgumentException,
            is IllegalStateException,
            -> ex.message
            else -> null
        }

        return buildResponse(
            httpStatus = HttpStatus.BAD_REQUEST,
            description = description,
        ).also {
            logger.error("Invalid Request: {}", ex.stackTraceToString())
        }
    }

    private fun buildResponse(
        httpStatus: HttpStatus,
        description: String? = null,
    ): ResponseEntity<ErrorResponseDto> =
        ResponseEntity.status(httpStatus).body(
            ErrorResponseDto(
                status = httpStatus.value(),
                message = httpStatus.reasonPhrase,
                description = description,
            ),
        )
}
