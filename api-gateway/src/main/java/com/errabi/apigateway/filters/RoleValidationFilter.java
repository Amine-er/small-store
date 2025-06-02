package com.errabi.apigateway.filters;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class RoleValidationFilter extends AbstractGatewayFilterFactory<RoleValidationFilter.Config> {

    public RoleValidationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.error("Missing or invalid Authorization header");
                return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }

            try {
                String token = authHeader.substring(7);
                JWT jwt = JWTParser.parse(token);
                Map<String, Object> claims = jwt.getJWTClaimsSet().getClaims();

                // Extract resource_access.myclient.roles
                Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
                if (resourceAccess == null || !resourceAccess.containsKey("myclient")) {
                    log.error("Missing resource_access for myclient");
                    return onError(exchange, "Missing resource_access for myclient", HttpStatus.FORBIDDEN);
                }

                Map<String, Object> myClientAccess = (Map<String, Object>) resourceAccess.get("myclient");
                List<String> roles = (List<String>) myClientAccess.get("roles");

                if (roles == null || !roles.contains("microservice1")) {
                    log.error("User does not have the required role: microservice1");
                    return onError(exchange, "User does not have the required role: microservice1", HttpStatus.FORBIDDEN);
                }

            } catch (ParseException e) {
                log.error("Invalid JWT token", e);
                return onError(exchange, "Invalid JWT token", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        // Configuration properties if needed
    }
}
