package com.github.blovemaple.hura.gpt;

import java.util.List;

/**
 * OpenAI Chat API 请求参数。
 *
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ChatRequest {
    private String model;
    private List<ChatMessage> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
