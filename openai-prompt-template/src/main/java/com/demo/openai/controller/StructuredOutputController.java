package com.demo.openai.controller;

import com.demo.openai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Ashwani Kumar
 * Created on 11/01/26.
 */
@RestController
@RequestMapping("/api/structured-output")
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient.Builder chatClientBuilder) { // creating new instance using builder for testing only
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/country-cities")
    public ResponseEntity<CountryCities> getCountryCities(@RequestParam("message") String message) {
        CountryCities countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                //.entity(CountryCities.class); //or
                .entity(new BeanOutputConverter<>(CountryCities.class));
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/list-cities")
    public ResponseEntity<List<String>> getCities(@RequestParam("message") String message) {
        List<String> cities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/map")
    public ResponseEntity<Map<String, Object>> getMap(@RequestParam("message") String message) {
        Map<String, Object> objectMap = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new MapOutputConverter());
        return ResponseEntity.ok(objectMap);
    }
}
