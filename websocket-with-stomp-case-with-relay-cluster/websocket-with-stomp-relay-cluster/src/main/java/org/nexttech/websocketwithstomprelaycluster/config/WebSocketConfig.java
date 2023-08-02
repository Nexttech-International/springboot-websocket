package org.nexttech.websocketwithstomprelaycluster.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexttech.websocketwithstomprelaycluster.handlers.WebSocketErrorHandler;
import org.nexttech.websocketwithstomprelaycluster.handlers.WebSocketHandshakeHandler;
import org.nexttech.websocketwithstomprelaycluster.interceptors.WebSocketChannelInterceptor;
import org.nexttech.websocketwithstomprelaycluster.interceptors.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

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
          Enable stomp relay using activemq cluster; All nodes will use the same user and password
         */
        final WebSocketProperties.RelayServer relayHost = webSocketProperties.getRelayServers().get(0);
        registry.enableStompBrokerRelay(webSocketProperties.getDestinationPrefixes())
                .setClientLogin(relayHost.getUsername())
                .setClientPasscode(relayHost.getPassword())
                .setSystemLogin(relayHost.getUsername())
                .setSystemPasscode(relayHost.getPassword())
                .setTcpClient(createTcpClient());

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

    /**
     * Create instance of {@link ReactorNettyTcpClient} with an available instance of relay server
     * This method will be called whenever a message has to be sent or a new subscription has to be created
     * If the first node is not available, the next one will be picked up from the list of relay servers.
     *
     * @return instance of {@link ReactorNettyTcpClient}
     */
    private ReactorNettyTcpClient<byte[]> createTcpClient() {
        return new ReactorNettyTcpClient<>(
                (client) -> client.remoteAddress(() -> {
                    final List<WebSocketProperties.RelayServer> relayServers = webSocketProperties.getRelayServers();

                    for (WebSocketProperties.RelayServer relayServer : relayServers) {
                        log.info("Picking available relay server and check its availability: {}:{}", relayServer.getHost(), relayServer.getPort());

                        try (Socket soc = new Socket()) {
                            final InetSocketAddress address = new InetSocketAddress(relayServer.getHost(), relayServer.getPort());
                            soc.connect(address, webSocketProperties.getRelayServerConnectTimeoutMillis());

                            // if no error is thrown, it means that the server is accessible, return that address
                            log.info("Found available relay server: {}:{}", relayServer.getHost(), relayServer.getPort());
                            return address;
                        } catch (IOException ex) {
                            log.error("Relay server {}:{} is not available, trying with another one", relayServer.getHost(), relayServer.getPort(), ex);
                        }
                    }

                    log.error("No relay server available. Returning the first server which will trigger the retry");
                    return new InetSocketAddress(relayServers.get(0).getHost(), relayServers.get(0).getPort());
                }),
                new StompReactorNettyCodec()
        );
    }
}
