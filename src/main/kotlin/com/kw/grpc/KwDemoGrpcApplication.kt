package com.kw.grpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KwDemoGrpcApplication

fun main(args: Array<String>) {
    runApplication<KwDemoGrpcApplication>(*args)
}
