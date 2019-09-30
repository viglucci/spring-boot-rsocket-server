package com.solidice.springbootrsocketserver.rpc;

import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdRequest;
import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdResponse;
import com.solidice.springbootrsocketserver.user.User;
import com.solidice.springbootrsocketserver.user.UserRepository;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
        GetUserByIdResponse response = GetUserByIdResponse
            .newBuilder()
            .setUser(
                com.solidice.springbootrsocketserver.rpc.proto.User
                    .newBuilder()
                    .setId(user.getId())
                    .setEmail(user.getEmail())
                    .setUsername(user.getUsername())
                    .build()
            )
            .build();
        return Mono.just(response);
    }
}
