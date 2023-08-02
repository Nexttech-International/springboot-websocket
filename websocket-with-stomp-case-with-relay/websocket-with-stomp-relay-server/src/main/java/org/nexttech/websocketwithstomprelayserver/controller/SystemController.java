package org.nexttech.websocketwithstomprelayserver.controller;

import lombok.RequiredArgsConstructor;
import org.nexttech.websocketwithstomprelayserver.model.ChatMessageRequestModel;
import org.nexttech.websocketwithstomprelayserver.service.WebSocketSenderService;
import org.springframework.http.HttpStatus;
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
}
