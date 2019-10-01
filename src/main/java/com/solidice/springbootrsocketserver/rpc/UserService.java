package com.solidice.springbootrsocketserver.rpc;

import com.google.protobuf.Empty;
import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdRequest;
import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdResponse;
import com.solidice.springbootrsocketserver.rpc.proto.UserMessage;
import com.solidice.springbootrsocketserver.user.User;
import com.solidice.springbootrsocketserver.user.UserRepository;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Slf4j
public class UserService implements com.solidice.springbootrsocketserver.rpc.proto.UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<GetUserByIdResponse> getUserById(GetUserByIdRequest message, ByteBuf metadata) {
        int userId = message.getUserId();
        User user = this.userRepository.getUserById(userId);
        UserMessage userMessage = UserMessage
            .newBuilder()
            .setId(user.getId())
            .setEmail(user.getEmail())
            .setUsername(user.getUsername())
            .build();
        GetUserByIdResponse response = GetUserByIdResponse
            .newBuilder()
            .setUser(userMessage)
            .build();
        return Mono.just(response);
    }

    @Override
    public Flux<GetUserByIdResponse> streamRandomUsers(Empty message, ByteBuf metadata) {
        log.info("Handling request for streamRandomUsers.");
        List<User> users = userRepository.getUsers();
        Stream<GetUserByIdResponse> responseStream = Stream.generate(() -> {
            Random rand = new Random();
            User user = users.get(rand.nextInt(users.size()));
            UserMessage userMessage = UserMessage
                .newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setUsername(user.getUsername())
                .build();
            return GetUserByIdResponse
                .newBuilder()
                .setUser(userMessage)
                .build();
        });
        return Flux.fromStream(responseStream).delayElements(Duration.ofMillis(500));
    }
}
