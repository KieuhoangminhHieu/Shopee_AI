package thuvien.spring_ai.dto.Request;

import java.util.List;

public class WeatherRequest {
    private String city;
    private List<String> cities;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<String> getCities() { return cities; }
    public void setCities(List<String> cities) { this.cities = cities; }
}