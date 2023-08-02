package org.nexttech.websocketwithstomprelayserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class WebSocketSenderServiceImpl implements WebSocketSenderService {
    private final SimpMessageSendingOperations messagingTemplate;

    public WebSocketSenderServiceImpl(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public <T> void sendMessageToTopic(String destination, T message, Map<String, Object> headers) {
        try {
            messagingTemplate.convertAndSend(destination, message, headers);
        } catch (Exception ex) {
            log.error("Error while sending message through websocket to destination {}.", destination, ex);
        }
    }
}
