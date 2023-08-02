package org.nexttech.websocketwithstomprelayserver.interceptors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor currentAccessor = getStompHeaderAccessor(message);

        if (Objects.nonNull(currentAccessor.getCommand())) {
            return switch (currentAccessor.getCommand()) {
                case CONNECT -> onConnect(message);
                case SUBSCRIBE -> onSubscribe(message);
                default -> message;
            };
        }

        return message;
    }

    private StompHeaderAccessor getStompHeaderAccessor(final Message<?> message) {
        return MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    }

    /**
     * Callback implementation for on CONNECT frame
     *
     * @param message - current instance of {@link Message}
     * @return existing instance of {@link Message} or null if the validation fails
     */
    private Message<?> onConnect(final Message<?> message) {
        log.info("onConnect method called");
        return message;
    }

    /**
     * Callback implementation for on SUBSCRIBE frame
     *
     * @param message - current instance of {@link Message}
     * @return existing instance of {@link Message} or null if the subscription fails
     */
    private Message<?> onSubscribe(final Message<?> message) {
        log.info("onSubscribe method called");
        return message;
    }
}
