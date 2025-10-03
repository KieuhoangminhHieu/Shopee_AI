package thuvien.spring_ai.dto.Response;

public class ChatResponse {
    private String content;
    private String model;
    private String timestamp;

    public ChatResponse(String content, String model, String timestamp) {
        this.content = content;
        this.model = model;
        this.timestamp = timestamp;
    }

    public String getContent() { return content; }
    public String getModel() { return model; }
    public String getTimestamp() { return timestamp; }
}