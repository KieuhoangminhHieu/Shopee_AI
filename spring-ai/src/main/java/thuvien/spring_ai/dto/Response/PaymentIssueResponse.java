package thuvien.spring_ai.dto.Response;

public record PaymentIssueResponse(String orderId, String issueType, String resolution, String nextStep) {
}
