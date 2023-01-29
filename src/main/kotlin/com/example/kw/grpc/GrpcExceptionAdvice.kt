package com.example.kw.grpc

import io.grpc.Status
import io.grpc.StatusException
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@GrpcAdvice
class GrpcExceptionAdvice {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GrpcExceptionHandler
    fun handle(ex: Exception): StatusException {
        logger.error("Grpc Exception: {}", ex.stackTraceToString())

        val status = Status.INTERNAL.withDescription(ex.message).withCause(ex)
        return status.asException()
    }
}
