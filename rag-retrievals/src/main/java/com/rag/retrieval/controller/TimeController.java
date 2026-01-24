package com.rag.retrieval.controller;

import com.rag.retrieval.tooling.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ashwani Kumar
 * Created on 23/01/26.
 */
@RestController
@RequestMapping("/api/time")
public class TimeController {

    private final ChatClient timeChatClient;
    private final TimeTools timeTools;


    public TimeController(ChatClient timeChatClient, TimeTools timeTools) {
        this.timeChatClient = timeChatClient;
        this.timeTools = timeTools;
    }

    @GetMapping("/local-time")
    public ResponseEntity<String> getCurrentTime(@RequestHeader String username, @RequestParam String message){
        String response = timeChatClient.prompt()
               // .tools(timeTools)
                .advisors(a -> a.param("conversationId", username))
                .user(message)
                .call().content();

        return ResponseEntity.ok(response);

    }



}
