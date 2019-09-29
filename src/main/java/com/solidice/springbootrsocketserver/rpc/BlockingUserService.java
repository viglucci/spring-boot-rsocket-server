package com.solidice.springbootrsocketserver.rpc;

import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdRequest;
import com.solidice.springbootrsocketserver.rpc.proto.GetUserByIdResponse;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlockingUserService implements com.solidice.springbootrsocketserver.rpc.proto.BlockingUserService {
    @Override
    public GetUserByIdResponse getUserById(GetUserByIdRequest message, ByteBuf metadata) {
        return null;
    }
}
