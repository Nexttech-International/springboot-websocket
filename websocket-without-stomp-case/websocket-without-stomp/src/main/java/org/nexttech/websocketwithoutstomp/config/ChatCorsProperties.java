package org.nexttech.websocketwithoutstomp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "chat.cors")
public class ChatCorsProperties {
    private String[] allowedOrigins;
}
