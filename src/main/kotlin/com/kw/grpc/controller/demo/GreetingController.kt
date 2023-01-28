package com.kw.grpc.controller.demo

import com.kw.grpc.service.demo.GreetingService
import com.kw.grpc.service.demo.HelloReply
import com.kw.grpc.service.demo.HelloRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController(
    private val greetingService: GreetingService,
) {

    @PostMapping("/say-hello")
    suspend fun sayHello(@RequestBody request: HelloRequest): HelloReply = greetingService.hello(request = request)
}
