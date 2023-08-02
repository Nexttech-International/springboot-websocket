package org.nexttech.websocketwithstomprelayserver.config;

import org.nexttech.websocketwithstomprelayserver.handlers.WebSocketErrorHandler;
import org.nexttech.websocketwithstomprelayserver.handlers.WebSocketHandshakeHandler;
import org.nexttech.websocketwithstomprelayserver.interceptors.WebSocketChannelInterceptor;
import org.nexttech.websocketwithstomprelayserver.interceptors.WebSocketHandshakeInterceptor;
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
