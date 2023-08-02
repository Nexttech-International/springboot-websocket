package org.nexttech.websocketwithstomprelayserver.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexttech.websocketwithstomprelayserver.handlers.WebSocketErrorHandler;
import org.nexttech.websocketwithstomprelayserver.handlers.WebSocketHandshakeHandler;
import org.nexttech.websocketwithstomprelayserver.interceptors.WebSocketChannelInterceptor;
import org.nexttech.websocketwithstomprelayserver.interceptors.WebSocketHandshakeInterceptor;
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
        /*
          Enable stomp relay;
         */
        final WebSocketProperties.RelayServer firstRelayHost = webSocketProperties.getRelayServers().get(0);
        registry.enableStompBrokerRelay(webSocketProperties.getDestinationPrefixes())
                .setRelayHost(firstRelayHost.getHost())
                .setRelayPort(firstRelayHost.getPort())
                .setClientLogin(firstRelayHost.getUsername())
                .setClientPasscode(firstRelayHost.getPassword())
                .setSystemLogin(firstRelayHost.getUsername())
                .setSystemPasscode(firstRelayHost.getPassword());

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
