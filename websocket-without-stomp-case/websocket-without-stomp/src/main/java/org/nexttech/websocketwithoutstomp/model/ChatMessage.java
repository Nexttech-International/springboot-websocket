package org.nexttech.websocketwithoutstomp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    private String name;
    private String text;
}
