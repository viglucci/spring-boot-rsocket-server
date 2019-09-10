import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import { UserServiceClient } from './generated/rsocket/UserService_rsocket_pb';
import { GetUserByIdRequest } from './generated/rsocket/UserService_pb';
import client from './RSocketClient';

// client.connect().subscribe({
//     onComplete: (rSocket) => {
//         console.log(rSocket);
//     },
//     onSubscribe: (_subscription) => {
//         console.log(_subscription)
//     },
//     onError: error => console.log(`RSocket error: ${error.message}`),
// });

// client.connect().subscribe({
//     onComplete: rsocket => {
//         console.log('onComplete');
//     },
//     onError: error => {
//         console.error("Failed to connect to local RSocket server.", error);
//     }
// });

client.connect().then((rsocket) => {
    const userService = new UserServiceClient(rsocket);
    const x = userService.getUser(new GetUserByIdRequest(1));
    console.log(x);
});

// const userService = new UserServiceClient(rsocket);

// userService.getUser(new GetUserByIdRequest(1)).subscribe({
//     onComplete: (res) => {
//         console.log(res);
//     },
//     onError: (error) => {
//         console.error(error);
//     }
// });

ReactDOM.render(<App/>, document.getElementById('root'));

