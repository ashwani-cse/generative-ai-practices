package com.rag.retrieval.advisors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

/**
 * @author Ashwani Kumar
 * Created on 09/01/26.
 */
public class TokenUsageAuditAdvisor implements CallAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(@NonNull ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse = chatClientResponse.chatResponse();
        assert chatResponse != null;
        ChatResponseMetadata responseMetadata = chatResponse.getMetadata();
        Usage usage = responseMetadata.getUsage();
        if (usage != null) {
            int promptTokens = usage.getPromptTokens();
            int completionTokens = usage.getCompletionTokens();
            int totalTokens = usage.getTotalTokens();
            logger.info("Token Usage - Prompt Tokens: {}, Completion Tokens: {}, Total Tokens: {}",
                    promptTokens, completionTokens, totalTokens);
            return chatClientResponse;
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "TokenUsagesAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
