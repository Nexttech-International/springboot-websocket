package org.nexttech.websocketwithstomprelayserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestModel {
    private String text;
    private String name;
}
