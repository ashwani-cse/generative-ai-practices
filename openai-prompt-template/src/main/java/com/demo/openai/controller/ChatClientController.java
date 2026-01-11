package com.demo.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ashwani Kumar
 * Created on 09/01/26.
 */
@RestController
@RequestMapping("/api")
public class ChatClientController {

    private final ChatClient chatClient;

    public ChatClientController(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {

       // return chatClient.prompt(message).call().content();

        // add system message as HR assistant
        return chatClient
                .prompt()
               // .system("You are an HR assistant who helps in the hiring process, provides suggestions on interview questions, and evaluates candidate answers only.")
              //  .options(OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_5_CHAT_LATEST).build())
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
