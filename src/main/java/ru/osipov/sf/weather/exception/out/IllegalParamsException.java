package ru.osipov.sf.weather.exception.out;

public class IllegalParamsException extends RuntimeException {

  public IllegalParamsException() {
    super("Invalid value");
  }
}
