package com.example.kw.grpc

import com.example.kw.grpc.Payment.ErrorResponse
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.google.protobuf.Any
import com.google.rpc.Code
import com.google.rpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@GrpcAdvice
class GrpcExceptionAdvice {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GrpcExceptionHandler(Exception::class)
    fun handelException(ex: Exception): StatusRuntimeException {
        logger.error("Grpc Exception: {}", ex.stackTraceToString())

        val code = Code.INTERNAL

        val status = Status.newBuilder()
            .setCode(code.number)
            .setMessage(code.name)
            .addDetails(Any.pack(buildResponse(code = code, description = ex.message)))
            .build()

        return StatusProto.toStatusRuntimeException(status)
    }

    @GrpcExceptionHandler(
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
    fun handelInvalidRequest(ex: Exception): StatusRuntimeException {
        val description: String? = when (ex) {
            is HttpMessageNotReadableException -> when (val cause = ex.cause) {
                is InvalidFormatException -> "${cause.path.last().fieldName} is missing or invalid"
                is MissingKotlinParameterException -> "${cause.path.last().fieldName} is missing or invalid"
                else -> ex.message
            }
            is IllegalArgumentException, is IllegalStateException -> ex.message
            else -> null
        }

        val code = Code.INVALID_ARGUMENT

        val status = Status.newBuilder()
            .setCode(code.number)
            .setMessage(code.name)
            .addDetails(Any.pack(buildResponse(code = code, description = description)))
            .build()

        return StatusProto.toStatusRuntimeException(status)
    }

    private fun buildResponse(
        code: Code,
        description: String? = null,
    ): ErrorResponse = ErrorResponse.newBuilder()
        .setStatus(code.number)
        .setMessage(code.name)
        .setDescription(description ?: "Fatal Error!")
        .build()
}
