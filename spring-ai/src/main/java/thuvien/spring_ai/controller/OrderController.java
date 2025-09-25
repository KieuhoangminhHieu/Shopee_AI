package thuvien.spring_ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import thuvien.spring_ai.dto.Request.OrderStatusRequest;
import thuvien.spring_ai.dto.Response.OrderStatusResponse;
import thuvien.spring_ai.service.OrderService;

public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/order-status")
    public OrderStatusResponse getOrderStatus(@RequestBody OrderStatusRequest request) {
        return orderService.extractOrderInfo(request);
    }
}
