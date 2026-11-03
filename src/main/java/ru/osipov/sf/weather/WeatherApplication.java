package ru.osipov.sf.weather;

import static ru.osipov.sf.weather.utils.ScannerInUtils.readDouble;
import static ru.osipov.sf.weather.utils.ScannerInUtils.readInteger;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.osipov.sf.weather.exception.out.IllegalParamsException;
import ru.osipov.sf.weather.mappers.WeatherMapper;
import ru.osipov.sf.weather.mappers.WeatherMapperImpl;
import ru.osipov.sf.weather.models.ResponseWeatherDTO;
import ru.osipov.sf.weather.service.WeatherService;
import ru.osipov.sf.weather.service.WeatherServiceImpl;

public class WeatherApplication {

  private static final WeatherService weatherService = new WeatherServiceImpl();
  private static final WeatherMapper weatherMapper = new WeatherMapperImpl();

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("------------------------------------------------------");
    System.out.println("\t\"Сервис по получению метеорологических данных\"");
    System.out.println("------------------------------------------------------");

    while (true) {
      Map<String, ? super Number> params = new HashMap<>();
      Double lat = readDouble("Введите широту (от -90 до 90): ");
      if (lat < -90 || lat > 90) throw new IllegalParamsException();

      Double lon = readDouble("Введите долготу (от -180 до 180): ");
      if (lon < -180 || lon > 180) throw new IllegalParamsException();

      Integer limit = readInteger("Введите срок прогноза (от 1 до 11): ");
      if (limit < 1 || limit > 11) throw new IllegalParamsException();

      params.put("limit", limit);

      HttpResponse<String> httpResponse = weatherService.getWeather(params);
      ResponseWeatherDTO dto = weatherMapper.toDto(httpResponse);

      System.out.printf(
          "Ответ в формате JSON: %s\nТекущая температура: %.0f ℃\nСредняя температура по прогнозу: %.2f ℃\n",
          httpResponse.body(), dto.getCurrentFactTemp(), calculateAverage(dto.getTempAvgList()));
      System.out.println("------------------------------------------------------");
    }
  }

  private static Double calculateAverage(List<Double> temperatures) {
    return temperatures.stream().mapToDouble(Double::doubleValue).sum() / temperatures.size();
  }
}
