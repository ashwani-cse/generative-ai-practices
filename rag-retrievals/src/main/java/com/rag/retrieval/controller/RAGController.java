package com.rag.retrieval.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ashwani Kumar
 * Created on 18/01/26.
 */
@RestController
@RequestMapping("/api/rag")
public class RAGController {

    private final ChatClient ragChatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/promptTemplates/sapiencePolicyTemplate.st")
    Resource promptTemplate;

    public RAGController(@Qualifier("ragChatClient") ChatClient chatClient,
                         VectorStore vectorStore) {
        this.ragChatClient = chatClient;
        this.vectorStore = vectorStore;
    }


    @GetMapping("/pre-retrieval/chat")
    public ResponseEntity<String> getResponse(@RequestHeader("username") String username,
                                                                   @RequestParam("message") String message) {
        String answer = ragChatClient
                .prompt()
                .advisors(aSpec -> aSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
