package org.nexttech.websocketwithstomprelaycluster.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.util.Objects;

@Slf4j
public class WebSocketErrorHandler extends StompSubProtocolErrorHandler {
    @Override
    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor, byte[] errorPayload, Throwable cause, StompHeaderAccessor clientHeaderAccessor) {
        if (Objects.nonNull(cause)) {
            log.error(cause.getMessage(), cause);
        }
        return super.handleInternal(errorHeaderAccessor, errorPayload, cause, clientHeaderAccessor);
    }
}
