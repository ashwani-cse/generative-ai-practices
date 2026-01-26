package com.mcp.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashwani Kumar
 * Created on 26/01/26.
 */
@RestController
@RequestMapping("/api")
public class MCPClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MCPClientController.class);

    private ChatClient chatClient;

    public MCPClientController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt().user(message).call().content();
    }

}
