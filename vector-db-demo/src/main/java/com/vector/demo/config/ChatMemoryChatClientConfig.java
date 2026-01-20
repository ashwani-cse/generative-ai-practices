package com.vector.demo.config;

import com.vector.demo.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {


    @Bean("chatMemoryChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 ChatMemory chatMemory) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsagesAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(
                        List.of(
                        loggerAdvisor,
                        memoryAdvisor,
                        tokenUsagesAuditAdvisor)
                )
                .build();
    }
// Creating another bean with different name for RAG Advisor demo purpose like
    // we can remove lot of boilerpat code from controller by using RetrievalAugmentationAdvisor
    @Bean("ragAdvisorChatClient")
    public ChatClient ragAdvisorChatClient(ChatClient.Builder chatClientBuilder,
                                 ChatMemory chatMemory,
                                 RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsagesAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(
                        List.of(
                                loggerAdvisor,
                                memoryAdvisor,
                                tokenUsagesAuditAdvisor,
                                retrievalAugmentationAdvisor)
                )
                .build();
    }

}
