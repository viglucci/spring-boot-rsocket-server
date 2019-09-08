package com.solidice.springbootrsocketserver.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
public class UsersRSocketController {

    @Autowired
    private UserService userService;

    @MessageMapping("usersList")
    public Mono<List<User>> usersList() {
        return this.userService.getUsers();
    }

    @MessageMapping("userById")
    public Mono<User> userById(GetUserByIdRequest request) { return this.userService.getUserById(request.getId()); };
}
