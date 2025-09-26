package thuvien.spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import thuvien.spring_ai.dto.Response.BillItem;
import thuvien.spring_ai.dto.Request.ChatRequest;
import java.util.List;

@Service
public class ChatService {
    private final ChatClient chatClient;
    private final JdbcChatMemoryRepository jdbcChatMemoryRepository;

    public ChatService(ChatClient.Builder builder, JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        this.jdbcChatMemoryRepository = jdbcChatMemoryRepository;

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(10)
                .build();
        chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public List<BillItem> chatWithImage(MultipartFile file, String message) {
        Media media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType(file.getContentType()))
                .data(file.getResource())
                .build();

        ChatOptions chatOptions = ChatOptions.builder()
                .temperature(1D)
                .build();

        return chatClient.prompt()
                .options(chatOptions)
                .system("You are ShopeeAI, a smart ecommerce assistant." +
                        "Your job is to help users find suitable products based on their needs." +
                        "Always respond in JSON format with a list of products including: name, category, price, and description." +
                        "Be concise, relevant, and avoid recommending unavailable items.")
                .user(promptUserSpec -> promptUserSpec.media(media)
                        .text(message))
                .call()
                .entity(new ParameterizedTypeReference<List<BillItem>>() {
                });
    }


    public String chat(ChatRequest request) {
        String conversationId = "conversation1";
        SystemMessage systemMessage = new SystemMessage("""
                You are ShopeeAI, a smart ecommerce assistant.
                Your job is to help users find suitable products based on their needs.
                Always respond in JSON format with a list of products including: name, category, price, and description.
                Be concise, relevant, and avoid recommending unavailable items.
                
                """);

        UserMessage userMessage = new UserMessage(request.message());

        Prompt prompt = new Prompt(systemMessage, userMessage);
        return chatClient.prompt(request.message())
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
