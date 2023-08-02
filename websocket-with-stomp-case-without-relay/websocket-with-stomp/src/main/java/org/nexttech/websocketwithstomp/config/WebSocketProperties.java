package org.nexttech.websocketwithstomp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "chat")
public class WebSocketProperties {
    private String applicationPrefix;
    private String[] allowedOrigins;
    private String[] destinationPrefixes;
    private String wsEndpoint;
}
