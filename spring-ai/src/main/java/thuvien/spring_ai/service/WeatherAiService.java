package thuvien.spring_ai.service;

import org.springframework.stereotype.Service;
import thuvien.spring_ai.dto.Request.WeatherRequest;
import thuvien.spring_ai.dto.Response.WeatherAdviceResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class WeatherAiService {

    private final ExecutorService aiExecutor;

    public WeatherAiService(ExecutorService aiExecutor) {
        this.aiExecutor = aiExecutor;
    }

    // ✅ Xử lý 1 thành phố
    public WeatherAdviceResponse generateAdvice(WeatherRequest request) {
        String city = request.getCity();
        String advice = "Thời tiết hôm nay ở " + city + " khá dễ chịu. Nhớ mang theo áo khoác nhẹ nếu ra ngoài nhé!";
        return new WeatherAdviceResponse(advice, city, LocalDateTime.now().toString());
    }

    // ✅ Xử lý nhiều thành phố song song
    public List<WeatherAdviceResponse> getBatchAdvice(List<String> cities) {
        long start = System.currentTimeMillis();

        List<CompletableFuture<WeatherAdviceResponse>> futures = cities.stream()
                .map(city -> CompletableFuture.supplyAsync(() -> {
                    String advice = "Thời tiết ở " + city + " ổn định. Ra ngoài thoải mái!";
                    return new WeatherAdviceResponse(advice, city, LocalDateTime.now().toString());
                }, aiExecutor))
                .toList();

        List<WeatherAdviceResponse> results = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        long end = System.currentTimeMillis();
        System.out.println("⏱️ Thời gian xử lý batch: " + (end - start) + "ms");

        return results;
    }
}