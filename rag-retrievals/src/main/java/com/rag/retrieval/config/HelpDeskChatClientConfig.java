package com.rag.retrieval.config;

import com.rag.retrieval.advisors.TokenUsageAuditAdvisor;
import com.rag.retrieval.tooling.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 25/01/26.
 */
@Configuration
public class HelpDeskChatClientConfig {

    @Value("classpath:/promptTemplates/helpDeskSystemPromptTemplate.st")
    private Resource systemPromptTemplate;

    @Bean("helpDeskChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,
                                 ChatMemory chatMemory,
                                 TimeTools timeTools) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsagesAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultSystem(systemPromptTemplate)
                .defaultAdvisors(List.of(loggerAdvisor,memoryAdvisor,tokenUsagesAuditAdvisor))
                .defaultTools(timeTools)
                .build();
    }
}
