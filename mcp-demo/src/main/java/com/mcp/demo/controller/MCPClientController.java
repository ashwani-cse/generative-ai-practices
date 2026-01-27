package com.mcp.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

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
    public String chat(@RequestHeader(value = "username", required = false)String username,
                       @RequestParam("message") String message) {
        return chatClient.prompt().user(message + "My username is "+username).call().content();
    }

}
