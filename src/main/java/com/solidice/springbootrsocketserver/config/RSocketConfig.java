package com.solidice.springbootrsocketserver.config;

import com.solidice.springbootrsocketserver.rpc.proto.BlockingUserServiceServer;
import com.solidice.springbootrsocketserver.rpc.proto.UserServiceServer;
import io.rsocket.RSocketFactory;
import io.rsocket.SocketAcceptor;
import io.rsocket.rpc.rsocket.RequestHandlingRSocket;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RSocketConfig {

    @Value("${rsocket.rpc.websocket.port}")
    Integer rpcRSocketWsServerTransportPort;

    @Bean
    public CloseableChannel rpcRSocket(
        @Qualifier("rpcSocketAcceptor") SocketAcceptor socketAcceptor,
        @Qualifier("rpcRSocketWsServerTransport") WebsocketServerTransport transport) {
        return RSocketFactory
            .receive()
            .acceptor(socketAcceptor)
            .transport(transport)
            .start()
            .block();
    }

    @Bean(name = "rpcSocketAcceptor")
    public SocketAcceptor rpcSocketAcceptor(
        UserServiceServer userServiceServer,
        BlockingUserServiceServer blockingUserServiceServer) {
        RequestHandlingRSocket requestHandlingRSocket = new RequestHandlingRSocket(
            userServiceServer,
            blockingUserServiceServer
        );
        return ((setup, sendingSocket) -> Mono.just(requestHandlingRSocket));
    }

    @Bean(name = "rpcRSocketWsServerTransport")
    public WebsocketServerTransport websocketServerTransport() {
        return WebsocketServerTransport.create(rpcRSocketWsServerTransportPort);
    }
}
