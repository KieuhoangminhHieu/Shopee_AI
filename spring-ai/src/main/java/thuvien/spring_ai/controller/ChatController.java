package thuvien.spring_ai.controller;

import org.springframework.web.bind.annotation.*;
import thuvien.spring_ai.dto.Request.ChatRequest;
import thuvien.spring_ai.dto.Response.ChatResponse;
import thuvien.spring_ai.service.ChatAiService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ai/chat")
public class ChatController {

    private final ChatAiService chatAiService;

    public ChatController(ChatAiService chatAiService) {
        this.chatAiService = chatAiService;
    }

    @PostMapping("/message")
    public ChatResponse chatWithAi(@RequestBody ChatRequest request) {
        return chatAiService.reply(request.getMessage());
    }
}