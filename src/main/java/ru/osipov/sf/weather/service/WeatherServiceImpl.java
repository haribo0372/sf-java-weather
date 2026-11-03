package ru.osipov.sf.weather.service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import ru.osipov.sf.weather.config.Config;
import ru.osipov.sf.weather.exception.out.HttpRequestException;

public class WeatherServiceImpl implements WeatherService {

  private static final Config config = new Config();

  private static String buildQuery(Map<String, ?> params) {
    return "?"
        + params.entrySet().stream()
            .map(
                e -> {
                  String param = URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=";
                  var value = e.getValue();
                  if (Objects.nonNull(value)) {
                    param += URLEncoder.encode(value.toString(), StandardCharsets.UTF_8);
                  }
                  return param;
                })
            .collect(Collectors.joining("&"));
  }

  @Override
  public HttpResponse<String> getWeather(Map<String, ?> params)
      throws IOException, InterruptedException {
    URI uri = URI.create(config.getApiWeatherUrl() + buildQuery(params));

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(uri)
            .header("X-Yandex-Weather-Key", config.getApiWeatherKey())
            .GET()
            .build();

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      return response;
    } else {
      throw new HttpRequestException(response.statusCode(), response.body());
    }
  }
}
