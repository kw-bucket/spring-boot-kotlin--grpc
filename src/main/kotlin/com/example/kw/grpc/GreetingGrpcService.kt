package com.example.kw.grpc

import com.example.kw.grpc.Greeting.HelloRequest
import com.example.kw.grpc.Greeting.HelloResponse
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class GreetingGrpcService : GreetingServiceGrpcKt.GreetingServiceCoroutineImplBase() {

    override suspend fun hello(request: HelloRequest): HelloResponse =
        HelloResponse.newBuilder().setMessage("Hi! ${request.name}! Welcome to gRPC!").build()
}
