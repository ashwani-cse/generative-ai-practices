package com.demo.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ashwani Kumar
 * Created on 09/01/26.
 */
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultSystem("""
                        You are an HR assistant who helps in the hiring process, provides suggestions
                        on interview questions, and evaluates candidate answers only.
                        """)
                .defaultUser("How can you help me ?.")
                .build();
    }
}
