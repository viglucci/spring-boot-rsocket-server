import {
    RSocketClient,
    BufferEncoders
} from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';

const transport = new RSocketWebSocketClient({
    url: 'ws://127.0.0.1:8081'
}, BufferEncoders);

// ms btw sending keepalive to server
const keepAlive = 60000 /* 60s in ms */;

// ms timeout if no keepalive response
const lifetime = 360000 /* 360s in ms */;

const client = new RSocketClient({
    setup: {
        keepAlive,
        lifetime,
    },
    transport
});

export default client;
