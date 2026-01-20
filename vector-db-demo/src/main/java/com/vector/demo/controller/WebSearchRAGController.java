package com.vector.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ashwani Kumar
 * Created on 19/01/26.
 */
@RestController
@RequestMapping("/api/rag/web-search")
public class WebSearchRAGController {

    private final ChatClient webSearchRAGChatClient;

    public WebSearchRAGController(ChatClient webSearchRAGChatClient) {
        this.webSearchRAGChatClient = webSearchRAGChatClient;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> getDataWithWeb(@RequestHeader("username") String username,
                                                             @RequestParam("message") String message) {
        String answer = webSearchRAGChatClient
                .prompt()
                .advisors(aSpec -> aSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
