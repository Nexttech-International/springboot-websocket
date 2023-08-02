package org.nexttech.websocketwithstomprelayserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "chat")
public class WebSocketProperties {
    private String applicationPrefix;
    private String[] allowedOrigins;
    private String[] destinationPrefixes;
    private String wsEndpoint;
    private List<RelayServer> relayServers;

    @Getter
    @Setter
    public static class RelayServer {
        private String host;
        private Integer port;
        private String username;
        private String password;
    }
}
