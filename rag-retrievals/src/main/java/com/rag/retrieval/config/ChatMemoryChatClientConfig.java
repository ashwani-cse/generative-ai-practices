package com.rag.retrieval.config;

import com.rag.retrieval.advisors.TokenUsageAuditAdvisor;
import com.rag.retrieval.util.PiiMaskingPostProcessor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
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
    public RetrievalAugmentationAdvisor retrievalAugAdvisor(VectorStore vectorStore,
                                                            ChatClient.Builder chatClientBuilder) {

        return RetrievalAugmentationAdvisor
                .builder()
                .queryTransformers( // By default open ai LLM provide multilingual support, this is just for demo
                        TranslationQueryTransformer.builder()
                                .chatClientBuilder(chatClientBuilder)
                                .targetLanguage("English")
                                .build()
                )
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .topK(3)
                        .similarityThreshold(0.5)
                        .build()
                )
                .documentPostProcessors(PiiMaskingPostProcessor.builder())
                .build();
    }

}
