package ru.osipov.sf.weather.service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public interface WeatherService {

  HttpResponse<String> getWeather(Map<String, ?> params) throws IOException, InterruptedException;
}
