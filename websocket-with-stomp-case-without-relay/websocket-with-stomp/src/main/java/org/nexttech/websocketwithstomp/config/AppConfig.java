package org.nexttech.websocketwithstomp.config;

import org.nexttech.websocketwithstomp.handlers.WebSocketErrorHandler;
import org.nexttech.websocketwithstomp.handlers.WebSocketHandshakeHandler;
import org.nexttech.websocketwithstomp.interceptors.WebSocketChannelInterceptor;
import org.nexttech.websocketwithstomp.interceptors.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public WebSocketHandshakeInterceptor webSocketHandshakeInterceptor() {
        return new WebSocketHandshakeInterceptor();
    }

    @Bean
    public WebSocketChannelInterceptor webSocketChannelInterceptor() {
        return new WebSocketChannelInterceptor();
    }

    @Bean
    public WebSocketHandshakeHandler webSocketHandshakeHandler() {
        return new WebSocketHandshakeHandler();
    }

    @Bean
    public WebSocketErrorHandler webSocketErrorHandler() {
        return new WebSocketErrorHandler();
    }
}
