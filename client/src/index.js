import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {UserServiceClient} from './generated/rsocket/UserService_rsocket_pb';
import {GetUserByIdRequest} from './generated/rsocket/UserService_pb';
import client from './RSocketClient';

client.connect().subscribe({
    onComplete: rsocket => {
        const service = new UserServiceClient(rsocket);
        const request = new GetUserByIdRequest([1]);
        service.getUserById(request).subscribe({
            onComplete: (response) => {
                console.log(response.getUser().toObject())
            },
            onError: (err) => {
                console.error(err);
            }
        });
        console.log("Success! We have established an RSocket connection.");
    },
    onError: error => {
        console.log("Failed to connect to local RSocket server.", error);
    }
});

ReactDOM.render(<App/>, document.getElementById('root'));

