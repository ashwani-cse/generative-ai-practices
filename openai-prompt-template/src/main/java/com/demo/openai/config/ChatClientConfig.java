package com.demo.openai.config;

import com.demo.openai.advisors.TokenUsagesAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 09/01/26.
 */
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                //.defaultAdvisors(new TokenUsagesAuditAdvisor())
                .defaultAdvisors(List.of(
                        new SimpleLoggerAdvisor(),
                        new TokenUsagesAuditAdvisor()))
                .defaultSystem("""
                        You are an HR assistant who helps in the hiring process, provides suggestions
                        on interview questions, and evaluates candidate answers only.
                        """)
                .defaultUser("How can you help me ?.")
                .build();
    }
}
