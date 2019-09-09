package com.solidice.springbootrsocketserver.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Slf4j
@Controller
public class UserRSocketController {

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("usersList")
    public Mono<List<User>> usersList() {
        log.info("Handling usersList request.");
        return Mono.just(this.userRepository.getUsers());
    }

    @MessageMapping("usersStream")
    Flux<User> usersStream(UserStreamRequest request) {
        log.info("Handling request for usersStream.");
        List<User> users = userRepository.getUsers();
        Stream<User> userStream = Stream.generate(() -> {
            Random rand = new Random();
            return users.get(rand.nextInt(users.size()));
        });
        return Flux.fromStream(userStream).delayElements(Duration.ofSeconds(1));
    }

    @MessageMapping("userById")
    public Mono<User> userById(GetUserByIdRequest request) {
        log.info("Handling request for userById id: {}.", request.getId());
        return Mono.just(this.userRepository.getUserById(request.getId()));
    };
}
