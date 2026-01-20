package com.rag.retrieval.config;

import com.rag.retrieval.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatMemoryChatClientConfig {


    @Bean("ragChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 ChatMemory chatMemory,
                                 RetrievalAugmentationAdvisor ragAdvisor) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsagesAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(
                        List.of(
                                loggerAdvisor,
                                memoryAdvisor,
                                tokenUsagesAuditAdvisor,
                                ragAdvisor)
                )
                .build();
    }

    @Bean("ragAdvisor")
    public RetrievalAugmentationAdvisor retrievalAugAdvisor(VectorStore vectorStore) {
        VectorStoreDocumentRetriever docRetriever
                = VectorStoreDocumentRetriever
                .builder()
                .vectorStore(vectorStore)
                .topK(3)
                .similarityThreshold(0.5)
                .build();

        return RetrievalAugmentationAdvisor
                .builder()
                .documentRetriever(docRetriever)
                .build();
    }

}
