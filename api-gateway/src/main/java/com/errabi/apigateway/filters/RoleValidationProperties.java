package com.errabi.apigateway.filters;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "role-validation")
public class RoleValidationProperties {
    private String requiredRole;
    private String clientName;
    private boolean enabled;
}
