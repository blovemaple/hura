package com.github.blovemaple.hura.gpt;

import java.util.List;

/**
 * OpenAI Chat API 请求响应。
 *
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
public class ChatResponse {
    public static class ChatResponseChoice {
        private ChatMessage message;

        public ChatMessage getMessage() {
            return message;
        }

        public void setMessage(ChatMessage message) {
            this.message = message;
        }
    }

    private List<ChatResponseChoice> choices;

    public List<ChatResponseChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<ChatResponseChoice> choices) {
        this.choices = choices;
    }

    public String getMessageContent() {
        if (choices == null || choices.isEmpty()) {
            return null;
        }
        ChatResponseChoice choice = choices.get(0);
        if (choice.getMessage() == null) {
            return null;
        }
        return choice.getMessage().getContent();
    }
}
