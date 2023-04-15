package com.github.blovemaple.hura.gpt;

import com.github.blovemaple.hura.util.PrivateConf;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

/**
 * OpenAI API接口。
 *
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@Component
public class OpenAiApi {
    @Autowired
    private PrivateConf privateConf;

    private Gson gson = new Gson();

    private HttpClient client = HttpClient.newBuilder()
            // .proxy(ProxySelector.of(new InetSocketAddress("127.0.0.1", 1081)))
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    public String chat(String message) throws IOException, InterruptedException {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");
        chatRequest.setMessages(List.of(new ChatMessage("user", message)));
        String chatRequestStr = gson.toJson(chatRequest);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .timeout(Duration.ofMinutes(2))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + privateConf.getOpenAiApiKey())
                .method("POST", HttpRequest.BodyPublishers.ofString(chatRequestStr))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            return "Error " + response.statusCode() + ": " + response.body();
        }

        ChatResponse chatResponse = null;
        try {
            chatResponse = gson.fromJson(response.body(), ChatResponse.class);
        } catch (JsonSyntaxException e) {
            return "Response Json Error: " + e.getMessage();
        }
        return chatResponse.getMessageContent();
    }
}
