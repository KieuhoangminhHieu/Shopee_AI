package thuvien.spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import thuvien.spring_ai.dto.Request.PaymentIssueRequest;
import thuvien.spring_ai.dto.Response.PaymentIssueResponse;

public class PaymentService {
    private final ChatClient chatClient;

    public PaymentService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    public PaymentIssueResponse handlePaymentIssue(PaymentIssueRequest request) {
        SystemMessage systemMessage = new SystemMessage("""
                You are ShopeeAI, an ecommerce assistant.
                Extract the order ID and identify the payment issue from the user's message.
                Respond in JSON format with fields: orderId, issueType, resolution, and nextStep.
                """);
        UserMessage userMessage = new UserMessage(request.message());
        Prompt prompt = new Prompt(systemMessage, userMessage);
        return chatClient.prompt(prompt).call().entity(PaymentIssueResponse.class);
    }
}
