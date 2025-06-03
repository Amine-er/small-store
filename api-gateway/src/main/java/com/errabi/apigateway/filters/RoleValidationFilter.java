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

    private final RoleValidationProperties properties;

    public RoleValidationFilter(RoleValidationProperties properties) {
        super(Config.class);
        this.properties = properties;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            if (!properties.isEnabled()) {
                log.info("Role validation filter is disabled");
                return chain.filter(exchange);
            }

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
                if (resourceAccess == null || !resourceAccess.containsKey(properties.getClientName())) {
                    log.error("Missing resource_access for {}", properties.getClientName());
                    return onError(exchange, "Missing resource_access for " + properties.getClientName(), HttpStatus.FORBIDDEN);
                }

                Map<String, Object> myClientAccess = (Map<String, Object>) resourceAccess.get(properties.getClientName());
                List<String> roles = (List<String>) myClientAccess.get("roles");

                if (roles == null || !roles.contains(properties.getRequiredRole())) {
                    log.error("User does not have the required role: {}", properties.getRequiredRole());
                    return onError(exchange, "User does not have the required role: " + properties.getRequiredRole(), HttpStatus.FORBIDDEN);
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
