/* eslint-disable */
// GENERATED CODE -- DO NOT EDIT!

'use strict';
var rsocket_rpc_frames = require('rsocket-rpc-frames');
var rsocket_rpc_core = require('rsocket-rpc-core');
var rsocket_rpc_tracing = require('rsocket-rpc-tracing');
var rsocket_rpc_metrics = require('rsocket-rpc-metrics').Metrics;
var rsocket_flowable = require('rsocket-flowable');
var UserService_pb = require('./UserService_pb.js');

var UserServiceClient = function () {
    function UserServiceClient(rs, tracer, meterRegistry) {
        this._rs = rs;
        this._tracer = tracer;
        this.getUserTrace = rsocket_rpc_tracing.traceSingle(tracer, "UserService", {"rsocket.rpc.service": "com.solidice.rsocket.example.UserService"}, {"method": "getUser"}, {"rsocket.rpc.role": "client"});
        this.getUserMetrics = rsocket_rpc_metrics.timedSingle(meterRegistry, "UserService", {"service": "com.solidice.rsocket.example.UserService"}, {"method": "getUser"}, {"role": "client"});
    }

    UserServiceClient.prototype.getUser = function getUser(message, metadata) {
        const map = {};
        return this.getUserMetrics(
            this.getUserTrace(map)(new rsocket_flowable.Single(subscriber => {
                    var dataBuf = Buffer.from(message.serializeBinary());
                    var tracingMetadata = rsocket_rpc_tracing.mapToBuffer(map);
                    var metadataBuf = rsocket_rpc_frames.encodeMetadata('com.solidice.rsocket.example.UserService', 'getUser', tracingMetadata, metadata || Buffer.alloc(0));
                    this._rs.requestResponse({
                        data: dataBuf,
                        metadata: metadataBuf
                    }).map(function (payload) {
                        //TODO: resolve either 'https://github.com/rsocket/rsocket-js/issues/19' or 'https://github.com/google/protobuf/issues/1319'
                        var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
                        return UserService_pb.GetUserByIdResponse.deserializeBinary(binary);
                    }).subscribe(subscriber);
                })
            )
        );
    };
    return UserServiceClient;
}();

exports.UserServiceClient = UserServiceClient;

var UserServiceServer = function () {
    function UserServiceServer(service, tracer, meterRegistry) {
        this._service = service;
        this._tracer = tracer;
        this.getUserTrace = rsocket_rpc_tracing.traceSingleAsChild(tracer, "UserService", {"rsocket.rpc.service": "com.solidice.rsocket.example.UserService"}, {"method": "getUser"}, {"rsocket.rpc.role": "server"});
        this.getUserMetrics = rsocket_rpc_metrics.timedSingle(meterRegistry, "UserService", {"service": "com.solidice.rsocket.example.UserService"}, {"method": "getUser"}, {"role": "server"});
        this._channelSwitch = (payload, restOfMessages) => {
            if (payload.metadata == null) {
                return rsocket_flowable.Flowable.error(new Error('metadata is empty'));
            }
            var method = rsocket_rpc_frames.getMethod(payload.metadata);
            var spanContext = rsocket_rpc_tracing.deserializeTraceData(this._tracer, payload.metadata);
            let deserializedMessages;
            switch (method) {
                default:
                    return rsocket_flowable.Flowable.error(new Error('unknown method'));
            }
        };
    }

    UserServiceServer.prototype.fireAndForget = function fireAndForget(payload) {
        throw new Error('fireAndForget() is not implemented');
    };
    UserServiceServer.prototype.requestResponse = function requestResponse(payload) {
        try {
            if (payload.metadata == null) {
                return rsocket_flowable.Single.error(new Error('metadata is empty'));
            }
            var method = rsocket_rpc_frames.getMethod(payload.metadata);
            var spanContext = rsocket_rpc_tracing.deserializeTraceData(this._tracer, payload.metadata);
            switch (method) {
                case 'getUser':
                    return this.getUserMetrics(
                        this.getUserTrace(spanContext)(new rsocket_flowable.Single(subscriber => {
                                var binary = !payload.data || payload.data.constructor === Buffer || payload.data.constructor === Uint8Array ? payload.data : new Uint8Array(payload.data);
                                return this._service
                                    .getUser(UserService_pb.GetUserByIdRequest.deserializeBinary(binary), payload.metadata)
                                    .map(function (message) {
                                        return {
                                            data: Buffer.from(message.serializeBinary()),
                                            metadata: Buffer.alloc(0)
                                        }
                                    }).subscribe(subscriber);
                            }
                            )
                        )
                    );
                default:
                    return rsocket_flowable.Single.error(new Error('unknown method'));
            }
        } catch (error) {
            return rsocket_flowable.Single.error(error);
        }
    };
    UserServiceServer.prototype.requestStream = function requestStream(payload) {
        return rsocket_flowable.Flowable.error(new Error('requestStream() is not implemented'));
    };
    UserServiceServer.prototype.requestChannel = function requestChannel(payloads) {
        return new rsocket_flowable.Flowable(s => payloads.subscribe(s)).lift(s =>
            new rsocket_rpc_core.SwitchTransformOperator(s, (payload, flowable) => this._channelSwitch(payload, flowable)),
        );
    };
    UserServiceServer.prototype.metadataPush = function metadataPush(payload) {
        return rsocket_flowable.Single.error(new Error('metadataPush() is not implemented'));
    };
    return UserServiceServer;
}();

exports.UserServiceServer = UserServiceServer;

