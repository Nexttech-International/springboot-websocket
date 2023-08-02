package org.nexttech.websocketwithstomp.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexttech.websocketwithstomp.handlers.WebSocketErrorHandler;
import org.nexttech.websocketwithstomp.handlers.WebSocketHandshakeHandler;
import org.nexttech.websocketwithstomp.interceptors.WebSocketChannelInterceptor;
import org.nexttech.websocketwithstomp.interceptors.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketProperties webSocketProperties;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final WebSocketChannelInterceptor webSocketChannelInterceptor;
    private final WebSocketHandshakeHandler webSocketHandshakeHandler;
    private final WebSocketErrorHandler webSocketErrorHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .setErrorHandler(webSocketErrorHandler)
                .addEndpoint(webSocketProperties.getWsEndpoint())
                .addInterceptors(webSocketHandshakeInterceptor)
                .setHandshakeHandler(webSocketHandshakeHandler)
                .setAllowedOrigins(webSocketProperties.getAllowedOrigins());

        registry
                .setErrorHandler(webSocketErrorHandler)
                .addEndpoint(webSocketProperties.getWsEndpoint())
                .addInterceptors(webSocketHandshakeInterceptor)
                .setHandshakeHandler(webSocketHandshakeHandler)
                .setAllowedOrigins(webSocketProperties.getAllowedOrigins())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(webSocketProperties.getDestinationPrefixes());

        /*
          The application destination prefix `/app` designates the broker to send
          messages prefixed with `/app` to our `@MessageMapping`s.
         */
        registry.setApplicationDestinationPrefixes(webSocketProperties.getApplicationPrefix());
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketChannelInterceptor);
    }
}
