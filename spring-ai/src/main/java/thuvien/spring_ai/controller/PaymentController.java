package thuvien.spring_ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import thuvien.spring_ai.dto.Request.PaymentIssueRequest;
import thuvien.spring_ai.dto.Response.PaymentIssueResponse;
import thuvien.spring_ai.service.PaymentService;

public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/payment-issue")
    public PaymentIssueResponse handlePayment(@RequestBody PaymentIssueRequest request) {
        return paymentService.handlePaymentIssue(request);
    }
}
