package org.demo.observability.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashwani Kumar
 * Created on 05/02/26.
 */
@RestController
@RequestMapping("/api")
public class ChatResponseController {

    private static final Logger log = LoggerFactory.getLogger(ChatResponseController.class);

    private final ChatClient chatClient;

    public ChatResponseController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String getResponse(@RequestParam("message") String message){
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}
