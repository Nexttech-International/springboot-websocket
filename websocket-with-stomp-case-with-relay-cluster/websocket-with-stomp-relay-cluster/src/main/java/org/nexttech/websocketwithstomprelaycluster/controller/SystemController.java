package org.nexttech.websocketwithstomprelaycluster.controller;

import lombok.RequiredArgsConstructor;
import org.nexttech.websocketwithstomprelaycluster.model.ChatMessageRequestModel;
import org.nexttech.websocketwithstomprelaycluster.service.WebSocketSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/mock")
@RequiredArgsConstructor
public class SystemController {
    private final WebSocketSenderService webSocketSenderService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendMockMessage(@RequestBody final ChatMessageRequestModel chatMessageRequestModel) {
        this.webSocketSenderService.sendMessageToTopic("/topic/chat", chatMessageRequestModel, Collections.emptyMap());
    }

    @MessageMapping("/using-annotation")
    @SendTo("/topic/chat")
    public ChatMessageRequestModel sendMockMessageUsingAnnotation(@Payload final ChatMessageRequestModel chatMessageRequestModel) {
        return chatMessageRequestModel;
    }
}
