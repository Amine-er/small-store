package com.errabi.microservice1.grpc;

import com.errabi.proto.grpc.HelloServiceGrpc;
import com.errabi.proto.grpc.HelloServiceProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private final Environment environment;

    public HelloServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void sayHello(HelloServiceProto.HelloRequest request, StreamObserver<HelloServiceProto.HelloResponse> responseObserver) {
        try {
            String port = environment.getProperty("local.server.port");
            String hostname = InetAddress.getLocalHost().getHostName();

            // Construct response
            String message = "Hello, " + request.getName() + "! from hostname: " + hostname + " and port: " + port;
            HelloServiceProto.HelloResponse response = HelloServiceProto.HelloResponse.newBuilder()
                    .setMessage(message)
                    .build();

            // Send response
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (UnknownHostException e) {
            responseObserver.onError(e);
        }
    }
}

