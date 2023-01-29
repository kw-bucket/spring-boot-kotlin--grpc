package com.example.kw.grpc.demo

import com.example.kw.grpc.demo.Greeting.HelloReply
import com.example.kw.grpc.demo.Greeting.HelloRequest
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class GreetingGrpcService : GreetingServiceGrpcKt.GreetingServiceCoroutineImplBase() {

    override suspend fun hello(request: HelloRequest): HelloReply =
        HelloReply.newBuilder().setMessage("Hi! ${request.name}! Welcome to gRPC!").build()
}
