package com.demo.openai.config;

import com.demo.openai.advisors.TokenUsagesAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
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
        // chat options provided here will be default for all chat prompts
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-4.1-mini")
               // .maxTokens(10) // limit response to 10 tokens, so delete this in real use case
                .temperature(0.8)
                .build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                //.defaultAdvisors(new TokenUsagesAuditAdvisor())
                .defaultAdvisors(List.of(
                        new SimpleLoggerAdvisor(),
                        new TokenUsagesAuditAdvisor()))
               // .defaultSystem("""
                 //       You are an HR assistant who helps in the hiring process, provides suggestions
                   //     on interview questions, and evaluates candidate answers only.
                     //   """)
                //.defaultUser("How can you help me ?.")
                .build();
    }
}
