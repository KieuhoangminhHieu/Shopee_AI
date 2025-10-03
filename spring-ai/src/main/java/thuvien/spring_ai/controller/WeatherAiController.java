package thuvien.spring_ai.controller;

import org.springframework.web.bind.annotation.*;
import thuvien.spring_ai.dto.Request.WeatherRequest;
import thuvien.spring_ai.dto.Response.WeatherAdviceResponse;
import thuvien.spring_ai.service.WeatherAiService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ai/weather")
public class WeatherAiController {

    private final WeatherAiService weatherAiService;

    public WeatherAiController(WeatherAiService weatherAiService) {
        this.weatherAiService = weatherAiService;
    }

    // ✅ Xử lý 1 thành phố
    @PostMapping("/advice")
    public WeatherAdviceResponse getSingleAdvice(@RequestBody WeatherRequest request) {
        return weatherAiService.generateAdvice(request);
    }

    // ✅ Xử lý nhiều thành phố song song
    @PostMapping("/batch")
    public List<WeatherAdviceResponse> getBatchAdvice(@RequestBody WeatherRequest request) {
        return weatherAiService.getBatchAdvice(request.getCities());
    }
}