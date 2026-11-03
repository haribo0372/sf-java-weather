package ru.osipov.sf.weather.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.http.HttpResponse;
import ru.osipov.sf.weather.models.ResponseWeatherDTO;

public interface WeatherMapper {

  ResponseWeatherDTO toDto(HttpResponse<String> response) throws JsonProcessingException;
}
