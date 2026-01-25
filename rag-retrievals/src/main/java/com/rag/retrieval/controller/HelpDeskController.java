package com.rag.retrieval.controller;

import com.rag.retrieval.tooling.HelpDeskTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Ashwani Kumar
 * Created on 25/01/26.
 */
@RestController
@RequestMapping("/api")
public class HelpDeskController {

    private final ChatClient helpDeskChatClient;
    private final HelpDeskTools helpDeskTools;


    public HelpDeskController(ChatClient helpDeskChatClient, HelpDeskTools helpDeskTools) {
        this.helpDeskChatClient = helpDeskChatClient;
        this.helpDeskTools = helpDeskTools;
    }

    @GetMapping("/help-desk")
    public ResponseEntity<String> helpDesk(@RequestHeader("username") String username,
                                           @RequestParam("message") String message) {
        String response = helpDeskChatClient
                .prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .tools(helpDeskTools)
                .toolContext(Map.of("username", username))
                .call()
                .content();
        return ResponseEntity.ok(response);
    }
}
