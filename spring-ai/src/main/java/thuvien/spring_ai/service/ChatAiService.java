package thuvien.spring_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import thuvien.spring_ai.dto.Response.ChatResponse;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class ChatAiService {

    private final ChatClient chatClient;
    private final ExecutorService aiExecutor;

    public ChatAiService(ChatClient.Builder builder, JdbcChatMemoryRepository memoryRepo, ExecutorService aiExecutor) {
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(memoryRepo)
                .maxMessages(10)
                .build();

        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        this.aiExecutor = aiExecutor;
    }

    public ChatResponse reply(String userMessage) {
        long start = System.currentTimeMillis();

        SystemMessage systemMessage = new SystemMessage("""
            Bạn là WeatherAI, một trợ lý thời tiết thông minh.
            Nhiệm vụ của bạn là phân tích dữ liệu thời tiết và đưa ra lời khuyên thân thiện, gần gũi cho người dùng.
            Hãy phản hồi bằng những câu ngắn gọn, mang tính trò chuyện, dựa trên nhiệt độ, độ ẩm, gió và điều kiện thời tiết.
            Tránh dùng thuật ngữ kỹ thuật. Hãy hữu ích và giao tiếp như một con người.
        """);

        Prompt prompt = new Prompt(systemMessage, new UserMessage(userMessage));

        String content;
        try {
            Future<String> future = aiExecutor.submit(() -> chatClient.prompt(prompt).call().content());
            content = future.get();
        } catch (Exception e) {
            content = "Xin lỗi, tôi đang gặp sự cố khi phản hồi.";
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("⏱️ Chat xử lý trong: " + (end - start) + "ms");

        return new ChatResponse(content, "gemini-2.0-flash", LocalDateTime.now().toString());
    }
}