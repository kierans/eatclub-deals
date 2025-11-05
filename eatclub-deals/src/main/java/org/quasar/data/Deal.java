package org.quasar.data;

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
}
