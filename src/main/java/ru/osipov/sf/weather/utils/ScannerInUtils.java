package ru.osipov.sf.weather.utils;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class ScannerInUtils {
  private static final String TEMPLATE_ERROR = "Ошибка: %s.\n";

  private static final Scanner scanner = new Scanner(System.in);

  public static Double readDouble(String title) {
    return read(title, s -> scanner.hasNextDouble(), s -> scanner.nextDouble());
  }

  public static Integer readInteger(String title) {
    return read(title, s -> scanner.hasNextInt(), s -> scanner.nextInt());
  }

  private static <T> T read(String title, Predicate<Scanner> predicate, Function<Scanner, T> func) {
    while (true) {
      System.out.print(title);
      if (predicate.test(scanner)) {
        return func.apply(scanner);
      } else {
        System.out.printf(TEMPLATE_ERROR, "Неверный формат");
      }
      scanner.next();
    }
  }
}
