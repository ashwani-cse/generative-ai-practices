package com.demo.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashwani Kumar
 * Created on 09/01/26.
 */
@RestController
@RequestMapping("/api/")
public class OpenAiController {

    private final ChatClient chatClient;

    public OpenAiController(ChatClient.Builder chatClientBuilder) {

        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        You are an HR assistant who helps in the hiring process, provides suggestions
                        on interview questions, and evaluates candidate answers only.
                        """)
                .build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {

       // return chatClient.prompt(message).call().content();

        // add system message as HR assistant
        return chatClient
                .prompt()
               // .system("You are an HR assistant who helps in the hiring process, provides suggestions on interview questions, and evaluates candidate answers only.")
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/chat/default-user")
    public String chatDefaultUserMessage(@RequestParam("message") String message) {
// user  message is ignored, default user message is used, so passing any message will have no effect
        // and see the behavior
        return chatClient
                .prompt()
                .call()
                .content();
    }
}
