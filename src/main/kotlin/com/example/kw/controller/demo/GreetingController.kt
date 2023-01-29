package com.example.kw.controller.demo

import com.example.kw.grpc.demo.Greeting.HelloReply
import com.example.kw.grpc.demo.Greeting.HelloRequest
import com.example.kw.grpc.demo.GreetingGrpcService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController(
    private val greetingService: GreetingGrpcService,
) {

    @PostMapping("/say-hello")
    suspend fun sayHello(@RequestBody request: HelloRequest): HelloReply = greetingService.hello(request = request)
}
