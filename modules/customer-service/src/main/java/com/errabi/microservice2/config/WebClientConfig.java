package com.errabi.microservice2.config;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
public class WebClientConfig {

    @Bean
    @Profile("!https")
    @LoadBalanced
    public WebClient.Builder webClientHttpBuilder(Tracer tracer, Propagator propagator) {
        HttpClient httpClient = HttpClient.create()
                .doOnRequest((req, conn) -> {
                    if (tracer.currentSpan() != null) {
                        propagator.inject(tracer.currentSpan().context(), req,
                                (carrier, key, value) -> carrier.requestHeaders().add(key, value)
                        );
                    }
                });

        return  WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }
    @Bean
    @Profile("https")
    @LoadBalanced
    public WebClient.Builder webClientBuilder() throws Exception {
        // Load the truststore from resources
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (InputStream trustStream = getClass().getClassLoader().getResourceAsStream("truststore.jks")) {
            if (trustStream == null) {
                throw new IllegalArgumentException("Keystore file 'truststore.jks' not found in the classpath");
            }
            trustStore.load(trustStream, "password".toCharArray());
            // Create TrustManagerFactory from truststore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            // Build SSL context for Netty
            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient()
                    .trustManager(trustManagerFactory);

            HttpClient httpClient = HttpClient.create()
                    .secure(sslContextSpec -> {
                        try {
                            sslContextSpec.sslContext(sslContextBuilder.build());
                        } catch (SSLException e) {
                            throw new RuntimeException(e);
                        }
                    });

            return WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient));
        }
    }
}
