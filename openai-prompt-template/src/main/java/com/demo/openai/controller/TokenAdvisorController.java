package com.demo.openai.controller;

import com.demo.openai.advisors.TokenUsagesAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashwani Kumar
 * Created on 09/01/26.
 */
@RestController
@RequestMapping("/api/")
public class TokenAdvisorController {

    private final ChatClient chatClient;

    public TokenAdvisorController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/token-usage")
    public String getTokenUsage(@RequestParam String message) {
        return chatClient
                .prompt()
               // .advisors(new TokenUsagesAuditAdvisor()) // if you want to add advisor only for this call
                .user(message)
                .call()
                .content();
    }
}
