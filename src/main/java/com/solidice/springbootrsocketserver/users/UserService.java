package com.solidice.springbootrsocketserver.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Mono<List<User>> getUsers() {
        return Mono.just(userRepository.getUsers());
    }

    public Mono<User> getUserById(int id) {
        return Mono.just(userRepository.getUserById(id));
    }
}
