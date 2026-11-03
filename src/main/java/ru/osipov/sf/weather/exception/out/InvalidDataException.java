package ru.osipov.sf.weather.exception.out;

public class InvalidDataException extends RuntimeException {

  public InvalidDataException(String message) {
    super(message);
  }
}
