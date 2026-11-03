package ru.osipov.sf.weather.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.Getter;

@Getter
public class Config {
  private final String apiWeatherUrl;
  private final String apiWeatherKey;

  public Config() {
    Properties props = new Properties();
    try (InputStream in =
        Config.class.getClassLoader().getResourceAsStream("application.properties")) {

      if (in == null) {
        throw new IllegalStateException("application.properties not found in classpath");
      }
      props.load(in);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load application.properties", e);
    }

    this.apiWeatherUrl = props.getProperty("api.weather.url");
    this.apiWeatherKey = props.getProperty("api.weather.key");

    if (this.apiWeatherKey == null || this.apiWeatherKey.isBlank()) {
      throw new IllegalStateException("api.weather.key is not set");
    }

    if (this.apiWeatherUrl == null || this.apiWeatherUrl.isBlank()) {
      throw new IllegalStateException("api.weather.url is not set");
    }
  }
}
