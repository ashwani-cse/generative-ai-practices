package com.vector.demo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ashwani Kumar
 * Created on 18/01/26.
 */
@RestController
@RequestMapping("/api/rag")
public class HRPolicyRAGController {

    private final ChatClient chatClient;
    private final ChatClient ragAdvisorChatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/promptTemplates/hrPolicyDataTemplate.st")
    Resource promptTemplate;

    public HRPolicyRAGController(@Qualifier("chatMemoryChatClient") ChatClient chatClient,
                                 @Qualifier("ragAdvisorChatClient") ChatClient ragAdvisorChatClient,
                                 VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.ragAdvisorChatClient = ragAdvisorChatClient;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/hr-policy/chat")
    public ResponseEntity<String> hrPolicyChat(@RequestHeader("username") String username,
                                             @RequestParam("message") String message) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(message)
                .topK(3) // to get top {n} similar documents, use this to limit the context size for cost optimization for LLM calls
                .similarityThreshold(0.5) // to consider the document which has a probability of 50% or more
                .build();

        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest); // get similar documents from vector store i.e. Qdrant in this case

        String similarContext = similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        String answer = chatClient
                .prompt()
                .system(promptSystemSpec ->
                        promptSystemSpec.text(promptTemplate) // prompt stuffing template file, see the file for {{documents}} placeholder
                                .param("documents", similarContext)) // value of similarContext will be set to {{documents}} placeholder in the template
                .advisors(advisorSpec ->
                        advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(answer);

    }

    /**
     * Using RAG Advisor for retrieval augmentation, see RAGAdvisorConfig.java for configuration
     * Purpose: To demonstrate how to use Retrieval Augmentation Advisor with Chat Client can be used to
     * get the similar documents from vector store automatically without manual intervention in the controller code.
     * This will reduce lot of boilerplate code in the controller.
     *
     */
    @GetMapping("/advisor/hr-policy/chat")
    public ResponseEntity<String> getHRPolicyUsingRAGAdvisor(@RequestHeader("username") String username,
                                                                   @RequestParam("message") String message) {
        String answer = ragAdvisorChatClient
                .prompt()
                .advisors(aSpec -> aSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
