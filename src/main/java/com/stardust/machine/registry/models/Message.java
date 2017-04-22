package com.stardust.machine.registry.models;

public class Message {
    public enum  MessageType {
        ERROR
    }

    private MessageType type;
    private String content;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
