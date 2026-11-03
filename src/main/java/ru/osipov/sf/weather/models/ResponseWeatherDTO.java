package ru.osipov.sf.weather.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWeatherDTO {

  private Double currentFactTemp;
  private List<Double> tempAvgList;
}
