package org.nexttech.websocketwithstomprelaycluster.config;

import org.nexttech.websocketwithstomprelaycluster.handlers.WebSocketErrorHandler;
import org.nexttech.websocketwithstomprelaycluster.handlers.WebSocketHandshakeHandler;
import org.nexttech.websocketwithstomprelaycluster.interceptors.WebSocketChannelInterceptor;
import org.nexttech.websocketwithstomprelaycluster.interceptors.WebSocketHandshakeInterceptor;
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
