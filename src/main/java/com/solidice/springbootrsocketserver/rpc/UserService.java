package com.solidice.springbootrsocketserver.rpc;

import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdRequest;
import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdResponse;
import com.solidice.springbootrsocketserver.user.UserRepository;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService implements com.solidice.springbootrsocketserver.rpc.proto.UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<GetUserByIdResponse> getUserById(GetUserByIdRequest message, ByteBuf metadata) {
        return null;
    }
}
