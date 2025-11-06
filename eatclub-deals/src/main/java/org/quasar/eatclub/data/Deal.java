package org.quasar.eatclub.data;

import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Predicate;

/*
 * It is assumed that if a deal does not have a start/end that it's TTL is
 * the opening hours of the restaurant.
 */
public record Deal(
  String objectId,
  String discount,
  Boolean dineIn,
  Boolean lightning,
  Optional<LocalTime> start,
  Optional<LocalTime> end,
  Long qtyLeft
) {
  /*
   * For a Deal to be considered available, its start time (if any) must be after the restaurant's open time.
   * You can't have a deal that starts before the restaurant opens.
   */
  public LocalTime getDealStart(final LocalTime restaurantOpen) {
    return selectTime(start, isAfter(restaurantOpen), restaurantOpen);
  }

  /*
   * A Deal's end time is either its own end time (if any) or the restaurant's close time.
   * You can't have a deal that's available for a closed restaurant.
   */
  public LocalTime getDealEnd(final LocalTime restaurantClose) {
    return selectTime(end, isBefore(restaurantClose), restaurantClose);
  }

  public static LocalTime selectTime(
    final Optional<LocalTime> time,
    final Predicate<LocalTime> predicate,
    final LocalTime def
  ) {
    return time.filter(predicate).orElse(def);
  }

  public static Predicate<LocalTime> isBefore(final LocalTime first) {
    return (second) -> second.isBefore(first);
  }

  public static Predicate<LocalTime> isAfter(final LocalTime first) {
    return (second) -> second.isAfter(first);
  }
}
