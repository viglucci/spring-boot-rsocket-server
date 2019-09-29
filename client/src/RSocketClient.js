import {
    RSocketClient,
    JsonSerializers,
    BufferEncoders
} from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';

const transport = new RSocketWebSocketClient({
    url: 'ws://127.0.0.1:8080/ws'
}, BufferEncoders);

const client = new RSocketClient({
    // send/receive JSON objects instead of strings/buffers
    // serializers: JsonSerializers,
    setup: {
        // ms btw sending keepalive to server
        keepAlive: 60000,

        // ms timeout if no keepalive response
        lifetime: 180000,

        // format of `data`
        dataMimeType: 'application/binary',

        // format of `metadata`
        metadataMimeType: 'message/x.rsocket.routing.v0',
    },
    transport
});

export default client;

// import {
//     BufferEncoders
// } from 'rsocket-core';
// import RSocketWebSocketClient from 'rsocket-websocket-client';
// const {
//     RequestHandlingRSocket,
//     RpcClient
// } = require('rsocket-rpc-core');
//
// const local = 'ws://127.0.0.1:8080/ws';
// const keepAlive = 60000 /* 60s in ms */;
// const lifetime = 360000 /* 360s in ms */;
//
// const transport = new RSocketWebSocketClient({ url: local }, BufferEncoders);
// const responder = new RequestHandlingRSocket(); // Will address this at the end
// const rsocketClient = new RpcClient({
//     setup: {
//         keepAlive,
//         lifetime,
//         dataMimeType: 'application/binary',
//         metadataMimeType: 'application/binary',
//     },
//     transport,
//     responder
// });
//
// export default rsocketClient;
