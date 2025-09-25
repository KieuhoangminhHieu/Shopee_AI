package thuvien.spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import thuvien.spring_ai.dto.Request.ReturnRequest;
import thuvien.spring_ai.dto.Response.ReturnResponse;

public class ReturnService {
    private final ChatClient chatClient;
    public ReturnService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }
    public ReturnResponse handleReturnRequest(ReturnRequest request) {
        SystemMessage systemMessage = new SystemMessage("""
                You are ShopeeAI, an ecommerce assistant.
                Extract the order ID and product name from the user's message.
                Determine whether the user wants to return or exchange the item.
                Respond in JSON format with fields: order Id, productName, action (return or exchange). and instructions.
                """);

        UserMessage userMessage = new UserMessage(request.message());
        Prompt prompt = new Prompt(systemMessage, userMessage);

        return chatClient.prompt(prompt).call().entity(ReturnResponse.class);
    }
}
