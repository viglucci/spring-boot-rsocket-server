import { RSocketClient, JsonSerializers } from 'rsocket-core';
import RSocketWebSocketClient from 'rsocket-websocket-client';

const transport = new RSocketWebSocketClient({
    url: 'ws://127.0.0.1:8080/ws'
});

const client = new RSocketClient({
    // send/receive JSON objects instead of strings/buffers
    serializers: JsonSerializers,
    setup: {
        // ms btw sending keepalive to server
        keepAlive: 60000,

        // ms timeout if no keepalive response
        lifetime: 180000,

        // format of `data`
        dataMimeType: 'application/json',

        // format of `metadata`
        metadataMimeType: 'application/json',
    },
    transport,
});

export default client;
