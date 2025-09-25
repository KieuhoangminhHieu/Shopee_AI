package thuvien.spring_ai.dto.Response;

public record OrderStatusResponse(String orderId, String status, String estimatedDeliveryDate) { }
