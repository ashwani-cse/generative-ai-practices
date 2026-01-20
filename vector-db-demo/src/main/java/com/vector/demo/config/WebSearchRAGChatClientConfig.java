package com.vector.demo.config;

import com.vector.demo.advisors.TokenUsageAuditAdvisor;
import com.vector.demo.rag.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 19/01/26.
 */
@Configuration
public class WebSearchRAGChatClientConfig {

    @Bean("webSearchRAGChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, RestClient.Builder restClientBuilder) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        WebSearchDocumentRetriever webSearchDocumentRetriever = WebSearchDocumentRetriever
                .builder()
                .restClientBuilder(restClientBuilder)
                .maxResults(5)
                .build();

        Advisor webSearchRAGAdvisor = RetrievalAugmentationAdvisor
                .builder()
                .documentRetriever(webSearchDocumentRetriever)
                .build();

        return chatClientBuilder
                .defaultAdvisors(List.of(
                        loggerAdvisor,
                        memoryAdvisor,
                        tokenUsageAdvisor,
                        webSearchRAGAdvisor)
                ).build();
    }
}
