package com.errabi.customer.service;

import com.errabi.proto.grpc.HelloServiceGrpc;
import com.errabi.proto.grpc.HelloServiceProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    @Value("${microservice1.grpc-server}")
    private String grpcServerAddress;

    public String consumeHello(String name) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(grpcServerAddress)
                .usePlaintext() // Disable TLS for simplicity
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

        // Create the request
        HelloServiceProto.HelloRequest request = HelloServiceProto.HelloRequest.newBuilder()
                .setName(name)
                .build();

        // Call the gRPC service
        HelloServiceProto.HelloResponse response = stub.sayHello(request);
        channel.shutdown();

        return response.getMessage();
    }
}

