package com.kw.grpc.service.demo

import org.lognet.springboot.grpc.GRpcService

@GRpcService
class GreetingService : GreetingServiceGrpcKt.GreetingServiceCoroutineImplBase() {

    override suspend fun hello(request: HelloRequest): HelloReply =
        HelloReply.newBuilder().setMessage("Hi! ${request.name}! Welcome to gRPC!").build()
}
