package com.solidice.springbootrsocketserver.greeting;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Controller
class GreetingsRSocketController {

    @MessageMapping("greet")
    Mono<GreetingsResponse> greet(GreetingsRequest request) {
        return Mono.just(new GreetingsResponse("Hello " + request.getName() + " @ " + Instant.now()));
    }

    @MessageMapping("greet-stream")
    Flux<GreetingsResponse> greetStream(GreetingsRequest request) {
        return Flux.fromStream(Stream.generate(
            () -> new GreetingsResponse("Hello " + request.getName() + " @ " + Instant.now())
        )).delayElements(Duration.ofSeconds(1));
    }
}
