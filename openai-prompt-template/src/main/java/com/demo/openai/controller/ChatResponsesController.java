package com.demo.openai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashwani Kumar
 * Created on 11/01/26.
 */
@RestController
@RequestMapping("/api/chat-responses")
public class ChatResponsesController {

    private static final Logger logger = LoggerFactory.getLogger(ChatResponsesController.class);

    private final ChatClient chatClient;

    public ChatResponsesController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/r1")
    public ChatResponse r1() {
        String prompt = "which model you are using currently ?";
        logger.info("Sending prompt to ChatClient: {}", prompt);
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(prompt)
                .call()
                //.content();
                .chatResponse();
        ;
        logger.info("Received chatResponse from ChatClient: {}", chatResponse);
        return chatResponse;
    }
    @GetMapping("/r2")
    public ChatClientResponse r2() {
        String prompt = "which model you are using currently ?";
        logger.info("Sending prompt to ChatClient: {}", prompt);
        ChatClientResponse chatClientResponse = chatClient
                .prompt()
                .user(prompt)
                .call()
                .chatClientResponse();
        ;
        logger.info("Received chatResponse from ChatClient: {}", chatClientResponse);
        return chatClientResponse;
    }

}
