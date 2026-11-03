package ru.osipov.sf.weather.exception.out;

public class HttpRequestException extends RuntimeException {

  public HttpRequestException(Integer status, String body) {
    super(String.format("HTTP error: status = %s, body = %s", status, body));
  }
}
