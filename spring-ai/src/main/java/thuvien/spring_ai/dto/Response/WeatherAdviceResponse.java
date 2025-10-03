package thuvien.spring_ai.dto.Response;

public class WeatherAdviceResponse {
    private String advice;
    private String city;
    private String timestamp;

    public WeatherAdviceResponse(String advice, String city, String timestamp) {
        this.advice = advice;
        this.city = city;
        this.timestamp = timestamp;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}