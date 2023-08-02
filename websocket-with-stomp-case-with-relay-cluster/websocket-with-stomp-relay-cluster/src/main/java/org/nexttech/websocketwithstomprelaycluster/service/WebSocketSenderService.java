package org.nexttech.websocketwithstomprelaycluster.service;

import java.util.Map;

public interface WebSocketSenderService {
    <T> void sendMessageToTopic(final String destination, final T message, final Map<String, Object> headers);
}
