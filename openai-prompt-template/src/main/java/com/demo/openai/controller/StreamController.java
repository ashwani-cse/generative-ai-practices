package com.demo.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author Ashwani Kumar
 * Created on 11/01/26.
 */
@RestController
@RequestMapping("/api/streams")
public class StreamController {

    private final ChatClient chatClient;

    public StreamController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
//http://localhost:8080/api/streams/s1?message=tell%20me%20about%20HR%20policy
    @GetMapping("/s1") // use this on chrome to see streaming effect,
    public Flux<String> stream(@RequestParam("message") String message) {
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();

    }
}
