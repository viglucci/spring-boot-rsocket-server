package com.solidice.rsocket.rpc;

import com.google.protobuf.Empty;
import com.solidice.rsocket.generated.proto.GetUserByIdRequestMessage;
import com.solidice.rsocket.generated.proto.GetUserByIdResponseMessage;
import com.solidice.rsocket.generated.proto.UserMessage;
import com.solidice.rsocket.generated.proto.UserService;
import com.solidice.rsocket.user.RandomUserEmitter;
import com.solidice.rsocket.user.User;
import com.solidice.rsocket.user.UserRepository;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RandomUserEmitter userEmitter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RandomUserEmitter userEmitter) {
        this.userEmitter = userEmitter;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<GetUserByIdResponseMessage> getUserById(
        GetUserByIdRequestMessage message,
        ByteBuf metadata) {

        int userId = message.getUserId();
        User user = this.userRepository.getUserById(userId);

        UserMessage userMessage = UserMessage
            .newBuilder()
            .setId(user.getId())
            .setEmail(user.getEmail())
            .setUsername(user.getUsername())
            .build();

        GetUserByIdResponseMessage response = GetUserByIdResponseMessage
            .newBuilder()
            .setUser(userMessage)
            .build();

        return Mono.just(response);
    }

    @Override
    public Flux<GetUserByIdResponseMessage> streamRandomUsers(Empty message, ByteBuf metadata) {
        log.info("Handling request for streamRandomUsers.");

        return this
            .userEmitter
            .getEmitter()
            .map(this::buildUserResponseMessage);
    }

    private GetUserByIdResponseMessage buildUserResponseMessage(User user) {
        UserMessage userMessage = UserMessage
            .newBuilder()
            .setId(user.getId())
            .setEmail(user.getEmail())
            .setUsername(user.getUsername())
            .build();

        return GetUserByIdResponseMessage
            .newBuilder()
            .setUser(userMessage)
            .build();
    }
}
