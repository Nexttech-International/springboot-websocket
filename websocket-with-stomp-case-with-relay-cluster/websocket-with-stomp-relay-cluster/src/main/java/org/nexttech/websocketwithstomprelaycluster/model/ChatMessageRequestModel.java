package org.nexttech.websocketwithstomprelaycluster.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestModel {
    private String text;
    private String name;
}
