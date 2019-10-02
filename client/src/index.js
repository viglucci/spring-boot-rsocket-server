import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {MAX_STREAM_ID} from 'rsocket-core';
import {UserServiceClient} from './generated/rsocket/UserService_rsocket_pb';
import {GetUserByIdRequestMessage} from './generated/rsocket/UserService_pb';
import {Empty} from 'google-protobuf/google/protobuf/empty_pb.js';

import client from './RSocketClient';

client.connect().subscribe({
    onComplete: rsocket => {
        const service = new UserServiceClient(rsocket);
        const getUserByIdRequest = new GetUserByIdRequestMessage([1]);
        service.getUserById(getUserByIdRequest).subscribe({
            onComplete: (response) => {
                console.log(response.getUser().toObject())
            },
            onError: (err) => {
                console.error(err);
            }
        });

        let streamSubscription = null;
        service.streamRandomUsers(new Empty()).subscribe({
            onComplete: () => console.log('done'),
            onError: error => console.error(error),
            onNext: value => {
                console.log(value.getUser().toObject());
            },
            // Nothing happens until `request(n)` is called
            onSubscribe: (subscription) => {
                streamSubscription = subscription;
                // send request(N=MAX_STREAM_ID) which is equal to max uint32, essentially subscribing to all events
                streamSubscription.request(MAX_STREAM_ID);
            }
        });

        console.log("Success! We have established an RSocket connection.");
    },
    onError: error => {
        console.log("Failed to connect to local RSocket server.", error);
    }
});

ReactDOM.render(<App/>, document.getElementById('root'));

