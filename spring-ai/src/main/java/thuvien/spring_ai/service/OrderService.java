package thuvien.spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import thuvien.spring_ai.dto.Request.OrderStatusRequest;
import thuvien.spring_ai.dto.Response.OrderStatusResponse;

public class OrderService {
    public final ChatClient chatClient;

    public OrderService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public OrderStatusResponse extractOrderInfo(OrderStatusRequest request) {
        SystemMessage systemMessage = new SystemMessage("""
                    You are ShopeeAI, an ecommerce assistant.
                    Extract the order ID from the user's message.
                    Respond only with the order ID in plain text, no explanation.
                """);
        UserMessage userMessage = new UserMessage(request.message());
        Prompt prompt = new Prompt(systemMessage, userMessage);
        String orderId = chatClient.prompt(prompt).call().content().trim();
        //Back-end lấy đơn hàng
        String status = "Đã giao";
        String estimatedDate = "25/9/2025";

        return new OrderStatusResponse(orderId, status, estimatedDate);

    }

}
