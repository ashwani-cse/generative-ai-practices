package org.demo.testingwithevaluators;

import org.demo.testingwithevaluators.controller.ChatResponseController;
import org.junit.jupiter.api.*;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {
        "spring.ai.openai.api-key=${OPENAI_API_KEY}",
        "logging.level.org.springframework.ai=DEBUG",
})
@SpringBootTest
class TestingWithEvaluatorsApplicationTests {

    @Autowired
    private ChatResponseController chatResponseController;
    @Autowired
    private ChatModel chatModel;

    private ChatClient chatClient;
    private RelevancyEvaluator relevancyEvaluator;
    private FactCheckingEvaluator factCheckingEvaluator;

    @Value("${test.relevancy.min-score:0.7}")
    private float minRelevancyScore;

    @BeforeEach
    void setup() {
        ChatClient.Builder chatClientBuilder = ChatClient.builder(chatModel).defaultAdvisors(new SimpleLoggerAdvisor());
        this.chatClient = chatClientBuilder.build();
        this.relevancyEvaluator = new RelevancyEvaluator(chatClientBuilder);
        this.factCheckingEvaluator = FactCheckingEvaluator.builder(chatClientBuilder).build();
    }

   // @Test
    @DisplayName("Should return relevant response for basic geography question")
    @Timeout(value = 30)
    void testChatResponseWithRelevancyEvaluator() {
        // Given
        String question = "What is the capital of France?";

        // When
        String aiResponse = chatResponseController.getResponse(question);

        // Then
        EvaluationRequest evaluationRequest = new EvaluationRequest(question, aiResponse);
        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
        Assertions.assertAll(
                ()-> assertThat(aiResponse).isNotBlank(),
                ()-> assertThat(evaluationResponse.isPass()).withFailMessage("""
                                ============================
                                The answer was not considered relevant.
                                Question: "%s"
                                AI Response: "%s"
                                """, question, aiResponse)
                        .isTrue(),
                ()-> assertThat(evaluationResponse.getScore()).withFailMessage("""
                                ============================
                                The relevancy score is lower than the minimum required %.2f.
                                Question: "%s"
                                AI Response: "%s"
                                """, evaluationResponse.getScore(), minRelevancyScore, question, aiResponse)
                        .isGreaterThan(minRelevancyScore));

    }

    @Test
    @DisplayName("Should return factually response for gravity related question")
    @Timeout(value = 300)
    void testFactAccuracyWithRelevancyEvaluator() {
        // Given
        String question = "Who discovered the law of universal gravitation?";

        // When
        String aiResponse = chatResponseController.getResponse(question);

        // Then
        EvaluationRequest evaluationRequest = new EvaluationRequest(question, aiResponse);
        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
        Assertions.assertAll(
                ()-> assertThat(aiResponse).isNotBlank(),
                ()-> assertThat(evaluationResponse.isPass()).withFailMessage("""
                                ============================
                                The answer was not considered factually correct.
                                Question: "%s"
                                AI Response: "%s"
                                """, question, aiResponse)
                        .isTrue());
    }

}
