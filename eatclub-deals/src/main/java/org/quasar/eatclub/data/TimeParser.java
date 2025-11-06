package org.quasar.eatclub.data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/*
 * Parses and formats times in the format "h:mma" ie: AM/PM time.
 *
 * For example, 10:00 is 10:00AM and 15:00 is 3:00PM
 */
public class TimeParser {
  private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendPattern("h:mma")
    .toFormatter(Locale.ENGLISH);

  public static LocalTime parseTime(final String value) {
    return LocalTime.parse(value, TIME_FORMATTER);
  }

  public static String format(final LocalTime time) {
    return time.format(TIME_FORMATTER);
  }
}
