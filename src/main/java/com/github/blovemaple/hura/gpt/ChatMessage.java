package com.github.blovemaple.hura.gpt;

/**
 * OpenAI Chat API 消息结构。
 *
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ChatMessage {
    private String role;
    private String content;

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
