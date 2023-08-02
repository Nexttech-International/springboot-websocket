package org.nexttech.websocketwithoutstomp.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nexttech.websocketwithoutstomp.model.ChatMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@RequiredArgsConstructor
public class JsonSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> activeSessions = new CopyOnWriteArrayList<>();

    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Handling new message...");
        final ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        for (WebSocketSession activeSession : activeSessions) {
            activeSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("New connection ready to use...");
        this.activeSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Closing existing connection...");
        this.activeSessions.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error(exception.getMessage(), exception);
    }
}