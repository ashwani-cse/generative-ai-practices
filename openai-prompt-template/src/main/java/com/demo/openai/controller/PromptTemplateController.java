package com.demo.openai.controller;

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
@RequestMapping("/api/prompt-template")
public class PromptTemplateController {

    private final ChatClient chatClient;


    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    String promptTemplate = """
            A customer named {customerName} has sent the following message:
            "{customerMessage}"
            Write a professional email response addressing the customer's concerns.
            Respond as if you are wrting the email body only. Don't include subject or signature.
            """;

    @GetMapping("/email")
    public String generateEmail(@RequestParam("customerName") String customerName,
                                @RequestParam("customerMessage") String customerMessage) {
        return chatClient
                .prompt()
                .system("""
                        You are a customer support assistant who helps in drafting professional 
                        email responses to customer inquiries.
                        """)
                .user(promptTemplateSpec ->
                        promptTemplateSpec.text(promptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage))
                .call()
                .content();
    }
}