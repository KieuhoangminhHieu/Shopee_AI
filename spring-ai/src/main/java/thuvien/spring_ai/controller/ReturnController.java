package thuvien.spring_ai.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import thuvien.spring_ai.dto.Request.ReturnRequest;
import thuvien.spring_ai.dto.Response.ReturnResponse;
import thuvien.spring_ai.service.ReturnService;

public class ReturnController {
    private final ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }
    @PostMapping("/return-request")
    public ReturnResponse handleReturn(@RequestBody ReturnRequest request) {
        return returnService.handleReturnRequest(request);
    }
}
