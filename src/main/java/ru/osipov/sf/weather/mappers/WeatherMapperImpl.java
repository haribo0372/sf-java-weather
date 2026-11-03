package ru.osipov.sf.weather.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import ru.osipov.sf.weather.exception.out.InvalidDataException;
import ru.osipov.sf.weather.models.ResponseWeatherDTO;

public class WeatherMapperImpl implements WeatherMapper {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Override
  public ResponseWeatherDTO toDto(HttpResponse<String> response) throws JsonProcessingException {
    JsonNode root = mapper.readTree(response.body());
    List<Double> avgTemps;

    JsonNode forecasts = root.path("forecasts");
    if (!forecasts.isArray()) {
      throw new InvalidDataException("Invalid data");
    }
    avgTemps = new ArrayList<>();
    for (JsonNode item : forecasts) {
      avgTemps.add(item.path("parts").path("day").path("temp_avg").asDouble());
    }

    return ResponseWeatherDTO.builder()
        .currentFactTemp(root.path("fact").path("temp").asDouble())
        .tempAvgList(avgTemps)
        .build();
  }
}
